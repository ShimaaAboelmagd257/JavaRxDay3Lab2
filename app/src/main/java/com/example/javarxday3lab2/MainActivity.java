package com.example.javarxday3lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText age;
    TextView displayText;
    Button displayButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        displayText = findViewById(R.id.displayText);
        displayButton = findViewById(R.id.displayButton);


        // when you press the display button
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // megrate the array lists with observable.fromArray();
                String names = name.getText().toString();
                String ages = age.getText().toString();

                Observable<String> namesObservable = Observable.fromArray(names);
                Observable<String> agesObservable = Observable.fromArray(ages);
                /* Zip functionality */
                /*combine the emissions of multiple Observables together via a specified function
                  and emit single items for each combination based on the results of this function
                  with the results of this function becoming the items emitted by the returned Observable
                 */
                //Observable.zip is an operator in RxJava that combines the emissions of multiple Observables into a single emission,
                Observable.zip(namesObservable, agesObservable, (name, age) -> {
                            return name + "  has  " + age + " years old ";
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String display) {
                                displayText.append(display + "  ");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }


                        });

            }
        });


    }
}
   // The Disposable can be used to cancel the subscription and free up any resources that were being used by the Observable. This is important for preventing memory leaks and ensuring that the application runs efficiently.