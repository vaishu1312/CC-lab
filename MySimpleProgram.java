package org.cloudbus.cloudsim.examples;
import java.util.*;
import java.text.*;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.*;
import org.cloudbus.cloudsim.provisioners.*;

public class MySimpleProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Cloudlet> cloudletlist = new ArrayList<Cloudlet>(); 
		List<Vm> vmlist = new ArrayList<Vm>();
		CloudSim.init(1,Calendar.getInstance() , false);
		Datacenter datacenter0 = createDataCenter("datacenter0");
		DatacenterBrokerSJF broker = createBroker();
		int brokerId = broker.getId();
		Vm vm = new Vm(0, brokerId, 1000, 1, 2048, 0, 1000, "Xen", new CloudletSchedulerSpaceShared());
		vmlist.add(vm);
		broker.submitVmList(vmlist);
		UtilizationModel um = new UtilizationModelFull();
		for (int i=0;i<15;i++)
		{
			int r = (int)(Math.random()*(500000-70000))+70000;
			Cloudlet cloudlet = new Cloudlet(i, r, 1, 300, 500, um, um, um);
			cloudlet.setVmId(0);
			cloudlet.setUserId(brokerId);
			cloudletlist.add(cloudlet);
			
		}
		/*Cloudlet cloudlet = new Cloudlet(0, 400000, 1, 300, 500, um, um, um);
		cloudlet.setVmId(0);
		cloudlet.setUserId(brokerId);
		cloudletlist.add(cloudlet);*/
		broker.submitCloudletList(cloudletlist);
		CloudSim.startSimulation();
		CloudSim.stopSimulation();
		List<Cloudlet> list = broker.getCloudletReceivedList();
		printStatus(list);
		

	}

	private static void printStatus(List<Cloudlet> list) {
		// TODO Auto-generated method stub
		
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}

	}
	

	private static DatacenterBrokerSJF createBroker() {
		// TODO Auto-generated method stub
		DatacenterBrokerSJF br=null;
		try {
			br = new DatacenterBrokerSJF("Broker");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return br;
	}

	private static Datacenter createDataCenter(String name) {
		// TODO Auto-generated method stub
		
		List<Host> hostlist = new ArrayList<Host>();
		List<Pe> pelist = new ArrayList<Pe>();
		
		pelist.add(new Pe(0, new PeProvisionerSimple(1000)));
		hostlist.add(new Host(0, new RamProvisionerSimple(2048), new BwProvisionerSimple(1000), 100000, pelist, new VmSchedulerTimeShared(pelist)));
		
		DatacenterCharacteristics characteristics = new DatacenterCharacteristics("x86","Linux", "Xen", hostlist, 10.0, 3.0, 0.05, 0.001, 0);
		
		Datacenter dc = null;
		try {
			dc = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostlist), null, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return dc;
	}

}
