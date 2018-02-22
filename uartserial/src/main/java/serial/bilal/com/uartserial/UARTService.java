package serial.bilal.com.uartserial;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.UartDevice;
import com.google.android.things.pio.UartDeviceCallback;

import java.io.IOException;

/**
 * Created by BLACK on 1/30/2018.
 */

public class UARTService extends UartDeviceCallback {

    private static final String TAG = UARTService.class.getSimpleName();

    private HandlerThread inputThread;
    private UartDevice mUartDevice;
    private OnSerialDataReceived onSerialDataReceived;

    private int chunkSize;

    public void Initialize(OnSerialDataReceived onSerialDataReceived, int baudRate, int dataBit, int stopBit, int chunkSize) {

        this.onSerialDataReceived = onSerialDataReceived;
        this.chunkSize = chunkSize;

        inputThread = new HandlerThread("UARTThread");
        inputThread.start();

        try {
            mUartDevice = new PeripheralManagerService().openUartDevice(BoardDefaults.getUartName());
            mUartDevice.setBaudrate(baudRate);
            mUartDevice.setDataSize(dataBit);
            mUartDevice.setParity(UartDevice.PARITY_NONE);
            mUartDevice.setStopBits(stopBit);
            mUartDevice.registerUartDeviceCallback(this, new Handler(inputThread.getLooper()));

            String ready = "Device is Ready !!\r\n";
            mUartDevice.write(ready.getBytes(), ready.length());

        } catch (IOException e) {
            Log.e(TAG, "Error while opening UART device", e);
        }
    }

    public void close() {
        onSerialDataReceived = null;
        try {
            mUartDevice.unregisterUartDeviceCallback(this);
            mUartDevice.close();
        } catch (IOException e) {
            Log.e(TAG, "Error closing UART device:", e);
        }

        inputThread.quitSafely();
    }

    @Override
    public boolean onUartDeviceDataAvailable(UartDevice uartDevice) {
        byte[] buffer = new byte[chunkSize];
        try {
            while (mUartDevice.read(buffer, buffer.length) > 0) {
                onSerialDataReceived.onDataReceived(uartDevice, buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onUartDeviceError(UartDevice uart, int error) {
        Log.w(TAG, uart + ": Error event " + error);
    }
}