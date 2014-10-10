package com.sanmixy.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sanmixy.dao.DeviceDao;
import com.sanmixy.dao.DoublePartCirDao;
import com.sanmixy.dao.HeadPartDao;
import com.sanmixy.dao.PartDao;
import com.sanmixy.dao.PentaPartCirDao;
import com.sanmixy.dao.QuadraPartCirDao;
import com.sanmixy.dao.TriplePartCirDao;
import com.sanmixy.dao.TwinsPartDao;
import com.sanmixy.http.GetSample;
import com.sanmixy.http.xmlHandle.IXMLReader;
import com.sanmixy.model.Device;
import com.sanmixy.model.DoublePartCir;
import com.sanmixy.model.HeadPart;
import com.sanmixy.model.Part;
import com.sanmixy.model.PentaPartCir;
import com.sanmixy.model.QuadraPartCir;
import com.sanmixy.model.TriplePartCir;
import com.sanmixy.model.Twins;
import com.sanmixy.utils.CommonUtils;
import com.sanmixy.utils.HibernateUtils;

public class DataStorage {
	
	private static BeanFactory beanFactory;
	
	static {
		
		beanFactory = new ClassPathXmlApplicationContext("applicationContext-*.xml");
		
	}

	public void partStore() {

		new IXMLReader().func();

	}

	public void rulesStore() {

		BufferedReader [] brs = new BufferedReader [5];
		String [] fileNames = new String [] {"./result/HeadPart.data",
											 "./result/DoublePart.data",
											 "./result/TriplePart.data",
											 "./result/QuadraPart.data",
											 "./result/PentaPart.data"};
		for (int ind = 1; ind <= 5; ind ++){
			
			try {
				brs[ind-1] = new BufferedReader (
						     new FileReader (
						     fileNames[ind-1]));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		headPartStore (brs[0]);
		doublePartCirStore (brs[1]);
		triplePartCirStore (brs[2]);
		quadraPartCirStore (brs[3]);
		pentaPartCirStore (brs[4]);
		
	}
	
	public void headPartStore (BufferedReader br){
		
		String temp = null;
		
		PartDao partDao = (PartDao)beanFactory.getBean("partDao");
		HeadPartDao headPartDao = (HeadPartDao)beanFactory.getBean("headPartDao");
		
		try {
			while (null != (temp = br.readLine())){
				
				String [] line = temp.split(" ");
				
				Double con = Double.parseDouble(line[0]);
				
				String headPartName = line[1];
				
				Part p = partDao.findPartByWholePartName(headPartName);
				
				HeadPart h = new HeadPart();
				
				h.setCon(con);
				h.setFirst(p);
				
				headPartDao.addHeadPart(h);
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doublePartCirStore (BufferedReader br){
		
		String temp = null;
		PartDao partDao = (PartDao) beanFactory.getBean("partDao");
		DoublePartCirDao doublePartCirDao = (DoublePartCirDao) beanFactory.getBean("doublePartCirDao");
		
		try {
			while (null != (temp = br.readLine())){
				
				String [] line = temp.split(" ");
				
				Double con = Double.parseDouble(line[0]);
				String f = line[1];
				String s = line[2];
				
				Part p1 = partDao.findPartByWholePartName(f);
				Part p2 = partDao.findPartByWholePartName(s);
				
				DoublePartCir d = new DoublePartCir();
				d.setFirst(p1);
				d.setSecond(p2);
				d.setCon(con);
				doublePartCirDao.addDoublePartCir(d);
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void triplePartCirStore (BufferedReader br){
		
		PartDao partDao = (PartDao) beanFactory.getBean("partDao");
		TriplePartCirDao triplePartCirDao = (TriplePartCirDao) beanFactory.getBean("triplePartCirDao");
		
		String temp = null;
		
		try {
			while (null != (temp = br.readLine())){
				
				String [] line = temp.split(" ");
				Double con = Double.parseDouble(line[0]);
				Part p1 = partDao.findPartByWholePartName(line[1]);
				Part p2 = partDao.findPartByWholePartName(line[2]);
				Part p3 = partDao.findPartByWholePartName(line[3]);
				TriplePartCir t = new TriplePartCir ();
				t.setFirst(p1);
				t.setSecond(p2);
				t.setThird(p3);
				t.setCon(con);
				
				triplePartCirDao.addTriplePartCir(t);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void quadraPartCirStore (BufferedReader br){
		
		QuadraPartCirDao quadraPartCirDao = (QuadraPartCirDao) beanFactory.getBean("quadraPartCirDao");
		
		PartDao partDao = (PartDao) beanFactory.getBean("partDao");
		
		String temp = null;
		
		try {
			while (null != (temp = br.readLine())){
				
				String [] line = temp.split(" ");
				
				Double con = Double.parseDouble(line[0]);
				
				Part p1 = partDao.findPartByWholePartName(line[1]);
				
				Part p2 = partDao.findPartByWholePartName(line[2]);
				
				Part p3 = partDao.findPartByWholePartName(line[3]);
				
				Part p4 = partDao.findPartByWholePartName(line[4]);
				
				QuadraPartCir q = new QuadraPartCir();
				q.setFirst(p1);
				q.setSecond(p2);
				q.setThird(p3);
				q.setForth(p4);
				q.setCon(con);
				quadraPartCirDao.addQuadraPartCir(q);
				
						
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pentaPartCirStore (BufferedReader br){
		
		PentaPartCirDao pentaPartCirDao = (PentaPartCirDao) beanFactory.getBean("pentaPartCirDao");
		
		PartDao partDao = (PartDao) beanFactory.getBean("partDao");
		
		String temp = null;
		
		try {
			while (null != (temp = br.readLine())){
				
				String [] line = temp.split(" ");
				Double con = Double.parseDouble(line[0]);
				
				Part p1 = partDao.findPartByWholePartName(line[1]);
				Part p2 = partDao.findPartByWholePartName(line[2]);
				Part p3 = partDao.findPartByWholePartName(line[3]);
				Part p4 = partDao.findPartByWholePartName(line[4]);
				Part p5 = partDao.findPartByWholePartName(line[5]);
				PentaPartCir p = new PentaPartCir ();
				
				p.setFirst(p1);
				p.setSecond(p2);
				p.setThird(p3);
				p.setForth(p4);
				p.setFifth(p5);
				p.setCon(con);
				
				pentaPartCirDao.addPentaPartCir(p);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deviceStore() {
		
		BufferedReader br = CommonUtils.getBufferedReader ("./source/allDevice(stage1).data");
		
		PartDao partDao = (PartDao)HibernateUtils.getBean("partDao");
		DeviceDao deviceDao = (DeviceDao)HibernateUtils.getBean("deviceDao");
		
		String temp = null;
		
		int index = 0;
		try {
			while (null != (temp = br.readLine())){
				index ++;
				
				
				String [] line = temp.split(":");
				
				Part p = (Part) partDao.findPartByWholePartName(line[0]);
				if (p == null){
					System.out.println();
					System.out.println("********");
					System.out.println(index);
					System.out.println("********");
					System.out.println();
					continue;
				}
				if (deviceDao.isDeviceExist(p.getPartName()) || 
					deviceDao.isSeqExist(line[1])){
					System.out.println();
					System.out.println("********");
					System.out.println(index);
					System.out.println("********");
					System.out.println();
					continue;
				}
				Device device = new Device ();
				device.setDevice(p);
				device.setPart_seq(line[1]);
				System.out.println(line[0]);
				
				deviceDao.addDevice(device);
				System.out.println();
				System.out.println("********");
				System.out.println(index);
				System.out.println("********");
				System.out.println();
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	public void twinsStore(String temp) {

		SAXReader saxReader = new SAXReader();

		File file = new File ("./data/"+temp+".xml");
		
		BufferedWriter bw = null;
		PartDao partDao = (PartDao) HibernateUtils.getBean("partDao");
		TwinsPartDao twinsPartDao = (TwinsPartDao) HibernateUtils.getBean("twinsPartDao");

		Document document = null;
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();	
		}

			// 获取根元素
		Element root = document.getRootElement();
		Element partList = root.element("part_list");
		Element part = partList.element("part");

		if (part != null) {
			if (part.element("twins") != null) {

				Part p = partDao.findPartByWholePartName(part
						.elementText("part_name"));

				for (Iterator iter = part.element("twins").elementIterator("twin"); 
					 iter.hasNext();) {

					Element twinElement = (Element) iter.next();

					Part q = partDao.findPartByWholePartName(twinElement
							.getText());
						
					System.out.println(twinElement.getText());

					if (q == null) {
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println(twinElement.getText());
						new GetSample().getData(twinElement.getText());
						new IXMLReader()
								.func(part.elementText(twinElement.getText()));
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println();
					}
						
					q = partDao.findPartByWholePartName(twinElement.getText());

					Twins twin = new Twins();
					twin.setBasePart(p);
					twin.setTwinPart(q);
					twinsPartDao.addTwinsPart(twin);

				}
			}
		}
	}
	

	public void twinsStore() {

		SAXReader saxReader = new SAXReader();

		File files = new File("./data");
		String[] fileNames = files.list();
		BufferedWriter bw = null;
		BeanFactory beanFactory = new ClassPathXmlApplicationContext(
				"applicationContext-*.xml");
		PartDao partDao = (PartDao) beanFactory.getBean("partDao");
		TwinsPartDao twinsPartDao = (TwinsPartDao) beanFactory
				.getBean("twinsPartDao");

		for (int index = 1; index <= fileNames.length; index++) {

			Document document = null;
			try {

				document = saxReader.read(new File("./data/"
						+ fileNames[index - 1]));
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			// 获取根元素
			Element root = document.getRootElement();
			Element partList = root.element("part_list");
			Element part = partList.element("part");
			System.out.println("!!!!!!!!!***********************");
			System.out.println("!!!!!!!!!!!****************"
					+ fileNames[index - 1]);
			System.out.println("!!!!!!!!!***********************");

			if (part != null) {
				if (part.element("twins") != null) {

					Part p = partDao.findPartByWholePartName(part
							.elementText("part_name"));

					for (Iterator iter = part.element("twins").elementIterator(
							"twin"); iter.hasNext();) {

						Element twinElement = (Element) iter.next();

						Part q = partDao.findPartByWholePartName(twinElement
								.getText());
						
						System.out.println(twinElement.getText());

						if (q == null) {
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println(twinElement.getText());
							new GetSample().getData(twinElement.getText());
							new IXMLReader()
									.func(part.elementText(twinElement.getText()));
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
							System.out.println();
						}
						
						q = partDao.findPartByWholePartName(twinElement
								.getText());

						Twins twin = new Twins();
						twin.setBasePart(p);
						twin.setTwinPart(q);
						twinsPartDao.addTwinsPart(twin);

					}
				}
			}
		}
	}

}
