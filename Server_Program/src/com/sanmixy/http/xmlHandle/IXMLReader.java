package com.sanmixy.http.xmlHandle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sanmixy.dao.PartDao;
import com.sanmixy.dao.TwinsPartDao;
import com.sanmixy.model.Part;
import com.sanmixy.model.Twins;
import com.sanmixy.utils.HibernateUtils;

public class IXMLReader {

	public void func() {

		SAXReader saxReader = new SAXReader();

		File files = new File("./data");
		String[] fileNames = files.list();
		BufferedWriter bw = null;
		
		PartDao partDao = (PartDao) HibernateUtils.getBean("partDao");
		

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

			System.out.println("Root: " + root.getName() + " "
					+ fileNames[index - 1]);
			try {

				if (part == null)
					continue;

				System.out.println(index + ":" + fileNames[index - 1]
						+ " !!!!!!!!!!!" + part.elementText("part_name")
						+ " *******" + part.elementText("part_url"));
				bw = new BufferedWriter(new FileWriter("./dataToHiber/"
						+ fileNames[index - 1] + ".data"));
				System.out.println(part.elementText("part_id") + " "
						+ part.elementText("part_name") + " "
						+ part.elementText("part_type") + " "
						+ part.elementText("part_url"));

				Part p = new Part();
				p.setPartID(Integer.parseInt(part.elementText("part_id")));
				p.setPartName(part.elementText("part_name"));
				p.setType(part.elementText("part_type"));
				p.setPartUrl(part.elementText("part_url"));
				partDao.addPart(p);

				bw.write(part.element("part_id").getText() + " ");
				bw.newLine();
				bw.write(part.element("part_name").getText() + " ");
				bw.newLine();
				bw.write(part.element("part_type").getText() + " ");
				bw.newLine();
				bw.write(part.element("part_url").getText() + " ");
				bw.newLine();
				bw.write(part.element("sequences").elementText("seq_data"));
				bw.newLine();
				bw.flush();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (null != bw)
						bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void func(String temp) {
		
		if (temp == null)
			return ;
		SAXReader saxReader = new SAXReader();

		File file = new File("./data/" + temp + ".xml");
		BufferedWriter bw = null;
		
		PartDao partDao = (PartDao) HibernateUtils.getBean("partDao");
		
		TwinsPartDao twinsPartDao = (TwinsPartDao)HibernateUtils.getBean("twinsPartDao");

		Document document = null;
		
		try {

			document = saxReader
					.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		// 获取根元素
		Element root = document.getRootElement();
		Element partList = root.element("part_list");
		Element part = partList.element("part");

		System.out.println("Root: " + root.getName());
		try {

			bw = new BufferedWriter(new FileWriter("./dataToHiber/"
					+ temp + ".xml.data"));
			System.out.println(part.elementText("part_id") + " "
					+ part.elementText("part_name") + " "
					+ part.elementText("part_type") + " "
					+ part.elementText("part_url"));

			Part p = new Part();
			p.setPartID(Integer.parseInt(part.elementText("part_id")));
			p.setPartName(part.elementText("part_name"));
			p.setType(part.elementText("part_type"));
			p.setPartUrl(part.elementText("part_url"));
			partDao.addPart(p);

			bw.write(part.element("part_id").getText() + " ");
			bw.newLine();
			bw.write(part.element("part_name").getText() + " ");
			bw.newLine();
			bw.write(part.element("part_type").getText() + " ");
			bw.newLine();
			bw.write(part.element("part_url").getText() + " ");
			bw.newLine();
			bw.write(part.element("sequences").elementText("seq_data"));
			bw.newLine();
			bw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (null != bw)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
