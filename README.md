# SensorBackground
Animated background using the accelerometer sensor.

## ScreenShots

--


## Include SensorBackground to Your Project

Step 1. Add the JitPack repository to your build file

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}

Step 2. Add the dependency

dependencies {
	        implementation 'com.github.Omurtekinn:SensorBackground:0.1.0'
	}

## Use SensorBackground in Layout File Just Like ImageView

```xml
<com.fevziomurtekin.SensorBackground
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/sens"
        android:src="@drawable/bg"/>
```
## Description of Attributes

All the attributes can also be set in java code:

```java
sensorBackground.setSplash(true);
sensorBackground.setSensorDelayTime(3000);
sensorBackground.setSensorDelayTime(1000);

```

## Create the SensorHelper

In Activity or Fragment using PanoramaImageView, you should __register the create in onResume()__ and __remember to delete it in onPause()__.

```java

public class MainActivity extends AppCompatActivity {

    private SensorBackground sensorBackground;
    private SensorHelper sensorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorBackground=findViewById(R.id.sens);
        sensorHelper=new SensorHelper();
        sensorBackground.setSensorHelper(sensorHelper);
        sensorBackground.setSplash(true);
        sensorBackground.setSensorDelayTime(3000);
        sensorBackground.setSensorDelayTime(1000);
        sensorBackground.setSensorScrollListener(new SensorBackground.OnSensorScrollListener() {
            @Override
            public void onScrolled(SensorBackground view, float offsetProgress) {
                Log.e("Main","girdi");
            }
        });

        sensorBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,secondActivitity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorHelper.create(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorHelper.delete(this);
    }
}
```



## License

    MIT License

    Copyright (c) 2018 Ömür Tekin

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

