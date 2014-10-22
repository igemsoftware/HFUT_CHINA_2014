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

/**
 * 
 * @author Xia Yu
 * @version 1.0
 * @brief Insert all the data into database.
 *
 */
public class DataStorage {
	
	/**< A spring BeanFactory, Which will offer all the DAO object to you. */
	private static BeanFactory beanFactory;
	
	/**
	 * @brief Initial the beanFactory.
	 */
	static {
		
		beanFactory = new ClassPathXmlApplicationContext("applicationContext-*.xml");
		
	}

	/**
	 * @breif Scan data from xml files and insert into PART table.
	 */
	public void partStore() {

		new IXMLReader().func();

	}

	/**
	 * @brief Insert all the rules into database, include the relations of all parts.
	 * @exception FileNotFoundException
	 */
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
	
	/**
	 * @param br(BufferedReader)
	 * @exception NumberFormatException, IOException
	 * @brief Insert the rules of head nodes in a part sequence.
	 * 
	 *  Scan data from files, and create the object that you want to save. 
	 *  Use the setter to initial all the attributes,And then use the dao 
	 *  object to save it.
	 */
	public void headPartStore (BufferedReader br){
		
		String temp = null;
		
		PartDao partDao = (PartDao)beanFactory.getBean("partDao");
		
		/**< Create a dao object with beanFactory object*/
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
	
	/**
	 * @exception NumberFormatException, IOException
	 * @param br (BufferedReader)
	 * @brief Insert the rules between two parts into database
	 * 
	 * Create the DoublePartCir object, and initial all the attributes, 
	 * then ues the DAO object to save it. 
	 */
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
	
	/**
	 * @exception NumberFormatException, IOException
	 * @param br (BufferedReader)
	 * @brief Insert the rules among three parts into database
	 * 
	 * Create the TriplePartCir object, and initial all the attributes, 
	 * then ues the DAO object to save it. 
	 */
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
	
	/**
	 * @exception NumberFormatException, IOException
	 * @param br (BufferedReader)
	 * @brief Insert the rules among four parts into database
	 * 
	 * Create the QuadraPartCir object, and initial all the attributes, 
	 * then ues the DAO object to save it. 
	 */
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
	
	/**
	 * @exception NumberFormatException, IOException
	 * @param br (BufferedReader)
	 * @brief Insert the rules among five parts into database
	 * 
	 * Create the PentaPartCir object, and initial all the attributes, 
	 * then ues the DAO object to save it. 
	 */
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

	/**
	 * @exception IOException
	 * @brief Insert the device data into database
	 * 
	 * Create a device object, and initial all the attributes,
	 * then save it by using device DAO object 
	 */
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
					continue;
				}
				if (deviceDao.isDeviceExist(p.getPartName()) || 
					deviceDao.isSeqExist(line[1])){
					continue;
				}
				Device device = new Device ();
				device.setDevice(p);
				device.setPart_seq(line[1]);
				if (device.getPart_seq().length() <= 255)
					deviceDao.addDevice(device);
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * @exception DocumentException
	 * @param temp
	 * @brief Insert twin data into database
	 * 
	 * Create a xml reader, and get the twin data of a part which is giving in the parameter,
	 * then save all the twins by DAO object.
	 */
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
						new GetSample().getData(twinElement.getText());
						new IXMLReader()
								.func(part.elementText(twinElement.getText()));
						
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
	

	/**
	 * @exception DocumentException
	 * @param temp
	 * @brief Insert twin data into database
	 * 
	 * Create a xml reader, and get the twin data of a part,
	 * then save all the twins by DAO object.
	 */
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
							new GetSample().getData(twinElement.getText());
							new IXMLReader()
									.func(part.elementText(twinElement.getText()));
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
