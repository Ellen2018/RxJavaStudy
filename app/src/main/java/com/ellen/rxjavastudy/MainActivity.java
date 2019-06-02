package com.ellen.rxjavastudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //简单的用法：被观察者->观察者->订阅
        //rxjava1();
        //简单的用法:链式调用
        //rxjava2();
        //简单用法：带线程切换，被观察者运行于io线程，观察者运行在子线程
        rxjava3();

        //背压策略->被观察者发送消息速度和观察者处理消息速度不匹配采用的一种解决策略
    }

    private void rxjava1() {
        //RxJava简单用法
        //1.创建一个被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("通过RxJava发送的消息");
                e.onNext("1");
                e.onNext("2");
                e.onComplete();
            }
        });
        //2.创建一个观察者
        Observer observer = new Observer<Object>() {

            //订阅成功时回调
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("RxJava","订阅成功");
            }

            //收到消息时回调
            @Override
            public void onNext(Object o) {
                Log.d("收到的消息",(String)o);
            }

            //发生错误是回调
            @Override
            public void onError(Throwable e) {

            }

            //完成时回调
            @Override
            public void onComplete() {
                Log.d("RxJava2","complete");
            }
        };
        //3.完成订阅关系
        observable.subscribe(observer);
    }

    private void rxjava2(){
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext("通过RxJava发送的消息");
                e.onNext("1");
                e.onNext("2");
                e.onComplete();
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.d("收到的消息",(String)o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("RxJava2","complete");
            }
        });
    }

    private void rxjava3() {
        Observable.create(new ObservableOnSubscribe<Object>() {

            //完成订阅关系的时候回调
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

            }
        }).subscribeOn(Schedulers.io())//被观察者运行在io线程
                .observeOn(Schedulers.newThread())//观察者运行在新的子线程
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
