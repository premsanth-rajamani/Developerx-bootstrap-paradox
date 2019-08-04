# Application X

## Problem Statement:
        Design a system to dynamically build and coorect catalog data from series of bill invoices collected by dunzo partner.
## Features:
        * the get requests are sent to Google Vision API to extract OCR data.
        * The extracted data is then labeled using a backend algorithm which uses Regular Expression, Vertices, LSTM       model.
        * In order to improve the accuracy of extracted data, we have crawler which extracts public information like Price, image for a particular product to enhance the catalog.
        * All the Catalog information is tored in a document based database MongoDB
        * User can search for a specific product from mobile app.
## Setup Instructions
    This zip contains  
    * appx.apk file which is the end app for Dunzo partners to upload vendor invoices
    * Backend folder contains 
        *server.js  which serves request from mobile application.
        * Crawler.js which is used to extract data from public sources.
        *parser.js file contains logic implemented for data labelling.
## Summary :

 The entire problem statement is splitted into modules
#### OCR
    * We used Google Vision API for OCR Data extraction.
#### Data Labeling / Figuring out Data
    * The google vision api uses block->paragrah->word->symbol approach. This might serve well for reading text from       document, but the structure is most times inconsistent and does not fit our problem statement.
    * We built algorithms using  Regex, vertices,crawlers.
    
#### Storing the Data
    * The extracted data is now stored in backend Database(MongoDB)
#### Visualizing the Data
    * The entire Catalog is visualized in mobile app even providing additional features for searching.