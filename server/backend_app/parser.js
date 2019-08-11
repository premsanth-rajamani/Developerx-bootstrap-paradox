let pinCode;
let tinNo;
let confidenceScore;

exports.init = xdata => {
  //console.log(JSON.stringify(xdata));
  //console.log(xdata["fullTextAnnotation"]);
  confidenceScore =
    xdata["fullTextAnnotation"]["pages"][0]["blocks"][0]["confidence"];
  if (true) {
    let productData = modify(xdata);
    let merchantData = extractData(xdata);
    return { productData, merchantData };
  } else {
    //console.log(`Confidence score is ${confidenceScore} whic is ;ess than 0.8`);
  }
};

function mergeRow(data) {
  const ymin = Math.min(...data[0].bounds.map(b => b.y));
  const ymax = Math.max(...data[0].bounds.map(b => b.y));
  const dist = (ymax - ymin) * 0.5;
  let arr = [];
  for (let i = 0; i < data.length; i++) {
    if (arr.length === 0) {
      arr.push([data[i]]);
    } else {
      let ymn = Math.min(...arr[arr.length - 1][0].bounds.map(b => b.y)) - dist;
      let ymx = Math.max(...arr[arr.length - 1][0].bounds.map(b => b.y)) + dist;
      const cond = !(
        data[i].bounds.some(b => b.y < ymn) ||
        data[i].bounds.some(b => b.y > ymx)
      );
      if (cond) {
        arr[arr.length - 1].push(data[i]);
      } else {
        arr.push([data[i]]);
      }
    }
  }
  arr.map(a => {
    return a.sort(function(a, b) {
      return a.bounds[0].x - b.bounds[0].x;
    });
  });
  return arr.map(ar => {
    let str = ar.map(a => a.symbols).join(" ");
    return str.split(" . ").join(".");
  });
}
function reArrange(data) {
  data.sort(function(a, b) {
    return a.bounds[0].y - b.bounds[0].y;
  });
  //console.log("RESULT:", mergeRow(data));
  return mergeRow(data);
}
const priceEnums = ["price", "rate", "rice", "prc"];
const quantityEnums = ["qty", "oty", "quantity", "qt", "ty"];
const totalEnums = ["amnt", "amt", "final", "amount", "total"];
const nameEnums = ["name", "item", "product", "iten", "nane"];
function checkFromEnum(val) {
  const enums = [...priceEnums, ...quantityEnums, ...totalEnums];
  const indx = enums.findIndex(e => e === val.toLocaleLowerCase());
  return indx !== -1;
}
function returnEnumKey(val) {
  const indx1 = priceEnums.findIndex(e => val.toLocaleLowerCase().includes(e));
  const indx2 = quantityEnums.findIndex(e =>
    val.toLocaleLowerCase().includes(e)
  );
  const indx3 = totalEnums.findIndex(e => val.toLocaleLowerCase().includes(e));
  const indx4 = nameEnums.findIndex(e => val.toLocaleLowerCase().includes(e));
  if (indx1 !== -1) {
    return "price";
  } else if (indx2 !== -1) {
    return "quantity";
  } else if (indx3 !== -1) {
    return "total";
  } else if (indx4 !== -1) {
    return "name";
  } else {
    return val;
  }
}
function getBillData(data) {
  let indx = 0;
  data.forEach((row, i) => {
    const title = row.split(" ").find(d => {
      return d.toLocaleLowerCase() === "price";
    });
    if (title) {
      indx = i;
      return;
    }
  });
  const final = data[indx].split(" ").map(e => {
    let isNumeric = false;
    if (checkFromEnum(e)) {
      isNumeric = true;
    }
    return {
      title: e,
      isNumeric,
      values: []
    };
  });
  let pattern1 = "([0-9]+(\\.[0-9]{0,3}\\s{0,}))";
  let finalarr = [];
  let dataCount = 0;
  final.forEach(e => {
    if (e.isNumeric) {
      dataCount += 1;
      finalarr.push(e);
    } else if (finalarr.length == 0) {
      finalarr.push(e);
    } else if (!finalarr[finalarr.length - 1].isNumeric) {
      finalarr[finalarr.length - 1].title += " " + e.title;
    }
  });
  let values = data.splice(indx);
  let End = [];
  let res = values.forEach(x => {
    const ptn = pattern1 + "{" + dataCount + "}";
    const regex = new RegExp(ptn, "gi");
    const mm = x.match(ptn);
    if (mm) {
      let obj = {};
      [x.replace(mm[0], ""), ...mm[0].split(" ")].forEach((e, i) => {
        finalarr[i] && finalarr[i].values.push(e);
        finalarr[i] && (obj[returnEnumKey(finalarr[i].title)] = e);
      });
      End.push(obj);
    }
  });
  //console.log("END: ", End);
  return End;
}
function modify(xdata) {
  const data = xdata.fullTextAnnotation.pages[0];
  let arrayOfWords = [];
  data.blocks.forEach(b => {
    b.paragraphs.forEach(p => {
      p.words.forEach(w => {
        const symbols = w.symbols
          .map(s => {
            return s.text;
          })
          .join("");
        arrayOfWords.push({ symbols, bounds: w.boundingBox.vertices });
      });
    });
  });
  const result = reArrange(arrayOfWords);
  //console.log(result);
  return getBillData(result);
}
// modify(xdata)

/*****************************************************************/
function extractData(xdata) {
  let merchantData = {};
  let allDescription = xdata["textAnnotations"][0].description;
  //   console.log(allDescription);
  //   console.log("************");

  //getting Shop Name
  let shopNamePattern = /^(.*)$/m;
  let shopName = allDescription.match(shopNamePattern)[0];
  //console.log(shopName);

  //getting pin Code
  try {
    let pinCodePattern = /([0-9]{6})/;
    pinCode = allDescription.match(pinCodePattern)[0];
  } catch (err) {
    if (err) pinCode = null;
  }
  merchantData["pinCode"] = pinCode;

  //getting Tin number
  try {
    let tinPattern = /([0-9]{11})/;
    tinNo = allDescription.match(tinPattern)[0];
  } catch (err) {
    if (err) tinNo = null;
  }
  merchantData["tinNo"] = tinNo;

  // //getting Address
  try {
    let shopAddress = allDescription
      .substring(
        allDescription.indexOf(shopName),
        allDescription.indexOf(pinCode)
      )
      .replace(shopName, "");
    merchantData["name"] = shopName.split(",").join(" ");
    merchantData["address"] = shopAddress.split(",").join(" ");
  } catch (err) {
    console.log("err" + err);
  }
  return merchantData;
}
