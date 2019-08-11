package com.developerx.base.data;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public final class RxBus {

    private static BehaviorSubject<Object> bus = BehaviorSubject.create();

    public static void send(Object o) {
        try {
            bus.onNext(o);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void send(ArrayList<Object> list){
        try {
            bus.onNext(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Observable<Object> toObservable() {
        return bus;
    }
}