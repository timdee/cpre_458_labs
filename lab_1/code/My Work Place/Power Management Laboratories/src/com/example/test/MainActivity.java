package com.example.test;

import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Handler;
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
		});

		//==========================================================================================

		// Action for Power Management 2 ===========================================================

		PMbutton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				OPERATIONmessage("[High Performance Mode] ###########################################");
				//TODO Please program for High Performance Mode here

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
		});

		//==========================================================================================

		// Action for Power Management 3 ===========================================================

		PMbutton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				OPERATIONmessage("[Dynamic Performance Mode] ========================================");
				//TODO Please program for Dynamic Frequency scaling Mode I or II or Mixed here.

				DATAname = "1300000"; // Setting up the minimum frequency 1300 Mhz
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

				//TODO Please program for CPU load.

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
	    	 OPERATIONmessage2("Error"+ex);
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

