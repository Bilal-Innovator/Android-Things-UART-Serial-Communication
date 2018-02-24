# Android Things UART Serial Communication Library
This library is designed and developed for Android Things to easily enable UART Serial communication by adding very few lines of code.

## How to use Android Things UART Serial Communication Library in your application
If you are building with Gradle, simply add the following line to the dependencies section of your build.gradle file:
```
dependencies {
     compile 'com.bilal:android-things-uart-communication:1.0.0'
}
```
## Getting Started with your own App

- Declare drivers permission in Manifest:
```
  <uses-permission android:name="com.google.android.things.permission.MANAGE_INPUT_DRIVERS" />
```

- Initialize Library :
```
  private final UARTService uartService = new UARTService();
    .
    .
    .  
       /** Constructor parameters (Communication parameters)
          uartService.Initialize(
          OnSerialDataReceived Listener,
          Baud Rate,
          Data Bit,
          Stop Bit,
          Data Chunk Size);
          **/

          //Initialize the UARTSerial Library and Communication parameters
          uartService.Initialize(this,
                 115200,
                  8,
                  1,
                  512);
```
 Note: Communication parameters changes accordingly the device whom which you are going to communicate. 
 Because most of the devices has their own communication parameters.
 
- Implement the OnSerialDataReceived, so when new data arrived you can read that from this Listener :
```
  implements OnSerialDataReceived
	  .
	  .
	  .
	  .
    @Override
    public void onDataReceived(UartDevice uartDevice, byte[] buffer) {
      try {
        String converted = new String(buffer, "UTF-8");
        Log.d(TAG, "onDataReceived() returned: " + converted);
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }
    }
```

- To write or send data to other deceives over UART, just call sendUARTData(byte.length) method as follows:

```
    String msgData = "Device is Ready !!\r\n";
    uartService.sendUARTData(msgData.getBytes(), msgData.length());
```

- When you are done with application, call uartService.close(); to free resources and stop the execution of the all Background Threads.

```
    @Override
    protected void onDestroy() {
      super.onDestroy();
      uartService.close();
    }
```
## License

Copyright 2017 Bilal Shaikh.

Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
