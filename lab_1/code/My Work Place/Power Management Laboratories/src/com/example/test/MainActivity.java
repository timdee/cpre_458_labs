package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	// Variables
    public Handler mHandler;
    public TextView OPmsg;
    
    public Button PMbutton1;
    public Button PMbutton2;
    public Button PMbutton3; 
    public Button CheckFrequency; 
    public Button CheckCPU;
    
    public String CPUname;
    public String DATAname;
    public String DATAaddress;
    public	float CPUusage = 0;

	private volatile boolean button_pressed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		PMbutton1 = (Button) findViewById(R.id.lab1);
		PMbutton2 = (Button) findViewById(R.id.lab2);
		PMbutton3 = (Button) findViewById(R.id.lab3);
		CheckFrequency = (Button) findViewById(R.id.checkFrequency);
		CheckCPU = (Button) findViewById(R.id.checkCPU);


		OPmsg = (TextView) findViewById(R.id.opMessage);

		mHandler = new Handler();


		final Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("su"); //get the permission from the super user.
		} catch (IOException e) {
		}


		// Action for Power Management 1 ===========================================================

		PMbutton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				button_pressed = false;
				setLowPerformanceMode();

			}
		});

		//==========================================================================================

		// Action for Power Management 2 ===========================================================

		PMbutton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				button_pressed = false;
				setHighPerformanceMode();

			}
		});

		//==========================================================================================

		// Action for Power Management 3 ===========================================================

		PMbutton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				OPERATIONmessage("[Dynamic Performance Mode] ========================================");
				//TODO Please program for Dynamic Frequency scaling Mode I or II or Mixed here. (done)
				button_pressed=true;

				(new Thread() {
					public void run() {
						// do stuff

				while(button_pressed == true) {
					// mode values table
					// value : meaning
					// 1 : Dynamic Frequency Scaling Mode I
					// 2 : Dynamic Frequency Scaling Mode II
					// 3 : Dynamic Frequency Scaling Mode Mixed
					int mode = 3;

					// determine the current load of the processors
					double load = ReadCPUload();

					//call functions which implement the different Dynamic Frequency scaling Modes
					if (mode == 1) {
						setDFS_1(load);
					} else if (mode == 2) {
						setDFS_2();
					} else if (mode == 3) {
						setDFS_Mixed(load);
					}

					try {
						this.sleep(1000);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
					}
				}).start();

				OPERATIONmessage("[Dynamic Performance Mode] ========================================");
			}
		});

		//==========================================================================================


		// Action for Check Frequency ===========================================================

		CheckFrequency.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				OPERATIONmessage("[Current Frequency] +++++++++++++++++++++++++++++");

				CPUname = "ALL";
				DATAname = "current min frequency";
				DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
				ReadCPUinfor(CPUname, DATAname, DATAaddress);
				DATAname = "current MAX frequency";
				DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
				ReadCPUinfor(CPUname, DATAname, DATAaddress);

				CPUname = "CPU0";
				DATAname = "current frequency";
				DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
				ReadCPUinfor(CPUname, DATAname, DATAaddress);

				CPUname = "CPU1";
				DATAname = "current frequency";
				DATAaddress = "/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq";
				ReadCPUinfor(CPUname, DATAname, DATAaddress);

				CPUname = "CPU2";
				DATAname = "current frequency";
				DATAaddress = "/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq";
				ReadCPUinfor(CPUname, DATAname, DATAaddress);

				CPUname = "CPU3";
				DATAname = "current frequency";
				DATAaddress = "/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq";
				ReadCPUinfor(CPUname, DATAname, DATAaddress);

				OPERATIONmessage("[Current Frequency] +++++++++++++++++++++++++++++");


			}
		});

		//==========================================================================================

		// Action for CPU usage Check ===========================================================

		CheckCPU.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				OPERATIONmessage("[Current CPU usage] ///////////////////////////////////////");
				//TODO Please program for CPU load. (done?)
				ReadCPUload();

				OPERATIONmessage("[Current CPU usage] ///////////////////////////////////////");
				return;

			}
		});

		//==========================================================================================

	}

	
protected void ChangeCPUinfor(String CPUname, String DATAname, String DATAaddress) {

	String cmd = "echo "+DATAname+" > "+ DATAaddress + "\n";
	Runtime runtime = Runtime.getRuntime();
	Process proc = null;
	OutputStreamWriter osw = null;
	
	 try {
	        proc = runtime.exec("su");
	        osw = new OutputStreamWriter(proc.getOutputStream());
	        osw.write(cmd);
	        osw.flush();
	        osw.close();
	    } catch (IOException ex) {
	    	 OPERATIONmessage2("Error" + ex);
	        return;
	    }
		
	}


protected CharSequence ReadCPUinfor(String CPUname, String DATAname, String DATAaddress) {
		
	ProcessBuilder cmd;
	  String result="";
	  
	  try{
	  
	   String[] args = {"/system/bin/cat", DATAaddress};
	   cmd = new ProcessBuilder(args);
	   
	   Process process = cmd.start();
	   InputStream in = process.getInputStream();
	   byte[] re = new byte[1024];
	   
	   while(in.read(re) != -1){
		 OPERATIONmessage2("["+CPUname+"] "+DATAname+" = "+new String(re));
	    //result = result + new String(re);
	   }
	   in.close();
	  } catch(IOException ex){
		  ex.printStackTrace();
	  }
	  return result;
	 }

protected double ReadCPUload() {

		ProcessBuilder cmd;
		double result=0.0;

		try{

			String[] args = {"/system/bin/cat", "/proc/stat"};
			cmd = new ProcessBuilder(args);

			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			String stat = "";

			while(in.read(re) != -1) {
				stat += new String(re);
				//read the entire input strea
				//result = result + new String(re);
			}

			in.close();

			// parse stat for the load data
			String load;
			Scanner stat_scanner = new Scanner(stat);

			load = stat_scanner.nextLine();
			String[] toks = stat_scanner.nextLine().split(" ");

			//do the cpu load calculation based on the first line of /proc/stat
			int user_time = Integer.valueOf(toks[1]);
			int nice_time = Integer.valueOf(toks[2]);
			int system_time = Integer.valueOf(toks[3]);
			int idle_time = Integer.valueOf(toks[4]);
			int iowait = Integer.valueOf(toks[5]);
			int irq = Integer.valueOf(toks[6]);
			int softirq = Integer.valueOf(toks[7]);
			int steal = Integer.valueOf(toks[8]);
			int guest = Integer.valueOf(toks[9]);
			int guest_nice = Integer.valueOf(toks[10]);

			// Guest time is already accounted in usertime
			int usertime = user_time - guest;
			int nicetime = nice_time - guest_nice;
			// Fields existing on kernels >= 2.6
			// (and RHEL's patched kernel 2.4...)
			int idlealltime = idle_time + iowait;
			int systemalltime = system_time + irq + softirq;
			int virtalltime = guest + guest_nice;
			int totaltime = usertime + nicetime + systemalltime + idlealltime + steal + virtalltime;

			result = ((double)(totaltime - idlealltime))/totaltime;
			OPERATIONmessage2("CPU load = "+ result + "\n");
		} catch(IOException ex){
			ex.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		Log.d("jfkfkldsj",Double.toString(result));
		return result;
	}

	/**
	 * low performance mode
	 */
	private void setLowPerformanceMode(){


		OPERATIONmessage("[Low Performance Mode] *************************************************");

		DATAname = "340000"; // Setting up the minimum frequency at 340 MHz
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
		ChangeCPUinfor(CPUname, DATAname, DATAaddress);
		DATAname = "340000"; // Setting up the maximum frequency at 340 MHz
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
		ChangeCPUinfor(CPUname, DATAname, DATAaddress);

		CPUname = "Low Power";
		DATAname = "current min frequency";
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
		ReadCPUinfor(CPUname, DATAname, DATAaddress);
		DATAname = "current MAX frequency";
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
		ReadCPUinfor(CPUname, DATAname, DATAaddress);

		OPERATIONmessage("[Low Performance Mode] *************************************************");
	}

	/**
	 * set high performance mode
	 */
	private void setHighPerformanceMode(){


		OPERATIONmessage("[High Performance Mode] ###########################################");
		//TODO Please program for High Performance Mode here (done)

		DATAname = "1300000"; // Setting up the minimum frequency 1300 Mhz
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
		ChangeCPUinfor(CPUname, DATAname, DATAaddress);
		DATAname = "1300000"; // Setting up the maximum frequency at 1300 MHz
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
		ChangeCPUinfor(CPUname, DATAname, DATAaddress);

		CPUname = "High Power";
		DATAname = "current min frequency";
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
		ReadCPUinfor(CPUname, DATAname, DATAaddress);
		DATAname = "current MAX frequency";
		DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
		ReadCPUinfor(CPUname, DATAname, DATAaddress);

		OPERATIONmessage("[High Performance Mode] ###########################################");

	}

	/**
	 * set processor to dynamic frequency scaling mode 1 (done)
	 * @param cpu_load
	 */
	private void setDFS_1(double cpu_load){
		// determine if I should go to a power mode
		if(cpu_load < .2){
			// go to low performance mode
			setLowPerformanceMode();
			Log.d("low","power");
		}else if(cpu_load > .9){
			//go to high performance mode
			setHighPerformanceMode();
			Log.d("High", "power");
		}else{
			// set the frequency range between 51 Mhz and 1.3 Ghz
			DATAname = "51000"; // Setting up the minimum frequency 51 Mhz
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
			ChangeCPUinfor(CPUname, DATAname, DATAaddress);
			DATAname = "1300000"; // Setting up the maximum frequency at 1300 MHz
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
			ChangeCPUinfor(CPUname, DATAname, DATAaddress);

			CPUname = "Dynamic Mode";
			DATAname = "current min frequency";
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
			ReadCPUinfor(CPUname, DATAname, DATAaddress);
			DATAname = "current MAX frequency";
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
			ReadCPUinfor(CPUname, DATAname, DATAaddress);
			Log.d("Middle", "power");
		}
	}

	/**
	 * set processor to dynamic frequency scaling mode 2 (done)
	 *
	 */
	private void setDFS_2(){
		// get the battery level
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = this.registerReceiver(null, ifilter);

		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		float battery_percent = level / (float)scale;

		// determine whether the screen is on
		boolean is_display_on = false;
		DisplayManager dm = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
		for (Display display : dm.getDisplays()) {
			if (display.getState() != Display.STATE_OFF) {
				is_display_on = true;
			}
		}

		// get the charging state
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean is_charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
				status == BatteryManager.BATTERY_STATUS_FULL;

		// determine if we are charging
		if(is_charging){
			// if we are charging we don't care about power usage.... set to high performance mode
			setHighPerformanceMode();
			Log.d("high","performance");
		}else if(battery_percent < .3) {
			// we want to conserve energy because we are almost out of it, set to low performance mode
			setLowPerformanceMode();
			Log.d("low","performance");
		}else{
				// if we're not charging and not low battery we have some decisions to make
				// if the wireless radio is on we are likely doing something online.
				// If we are doing something we will like a more responsive device
				// so allow the processor to vary between two fairly high frequency states
				if (is_display_on) {
					// set to fairly high processing state
					// set the frequency range between 700 Mhz and 1.3 Ghz
					DATAname = "700000"; // Setting up the minimum frequency 51 Mhz
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
					ChangeCPUinfor(CPUname, DATAname, DATAaddress);
					DATAname = "1300000"; // Setting up the maximum frequency at 1300 MHz
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
					ChangeCPUinfor(CPUname, DATAname, DATAaddress);

					CPUname = "Dynamic Mode";
					DATAname = "current min frequency";
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
					ReadCPUinfor(CPUname, DATAname, DATAaddress);
					DATAname = "current MAX frequency";
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
					ReadCPUinfor(CPUname, DATAname, DATAaddress);
				} else {
					// set to lower processing state
					// set the frequency range between 51 Mhz and 700 Mhz
					DATAname = "51000"; // Setting up the minimum frequency 51 Mhz
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
					ChangeCPUinfor(CPUname, DATAname, DATAaddress);
					DATAname = "700000"; // Setting up the maximum frequency at 1300 MHz
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
					ChangeCPUinfor(CPUname, DATAname, DATAaddress);

					CPUname = "Dynamic Mode";
					DATAname = "current min frequency";
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
					ReadCPUinfor(CPUname, DATAname, DATAaddress);
					DATAname = "current MAX frequency";
					DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
					ReadCPUinfor(CPUname, DATAname, DATAaddress);
				}
			Log.d("Moderate","Performance");
		}
	}

	/**
	 * set processor to dynamic frequency scaling mode mixed
	 * @param cpu_load
	 */
	private void setDFS_Mixed(double cpu_load) {
		// get the battery level
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = this.registerReceiver(null, ifilter);

		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		float battery_percent = level / (float)scale;

		// get the charging state
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean is_charging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
				status == BatteryManager.BATTERY_STATUS_FULL;

		// determine if we are charging
		if(is_charging){
			// if we are charging we don't care about power usage.... set to high performance mode
			setHighPerformanceMode();
		}else {
			// vary the processor frequency based on the battery level.
			// the higher the battery level, the higher the frequency.
			// anywhere from 100000 khz to 1300000 khz
			double step_size = 1.0/12.0;

			// pretend like the cpu_load is less if the battery percent is lower
			int load_fraction = new Double(Math.ceil( (cpu_load * battery_percent) / step_size)).intValue();

			// load_fraction is at greatest 1
			int high_frequency = 100000 * (Integer.valueOf(load_fraction) + 1);
			int low_frequency = high_frequency;

			Log.d("low_frequency",new Integer(low_frequency).toString());
			Log.d("high_frequency", new Integer(high_frequency).toString());

			// set to fairly high processing state
			DATAname = String.valueOf(low_frequency); // Setting up the minimum frequency at low frequency
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
			ChangeCPUinfor(CPUname, DATAname, DATAaddress);
			DATAname = String.valueOf(high_frequency); // Setting up the maximum frequency at high frequency
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
			ChangeCPUinfor(CPUname, DATAname, DATAaddress);

			CPUname = "Dynamic Mode";
			DATAname = "current min frequency";
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
			ReadCPUinfor(CPUname, DATAname, DATAaddress);
			DATAname = "current MAX frequency";
			DATAaddress = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
			ReadCPUinfor(CPUname, DATAname, DATAaddress);
		}
	}

//******************************************* [Operation message] ********************************************
	
		private void OPERATIONmessage(final String text) {
				
			mHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						OPmsg.append(text+"\n");  
					}
					
				});		
				
			}
		
//**********************************************************************************************************  	


		private void OPERATIONmessage2(final String text) {
			
			mHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						OPmsg.append(text);  
					}
					
				});		
				
			}
		
		
	
}// END of public class MainActivity extends Activity

