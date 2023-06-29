package com.pravna_informatika.projekat.service;

import com.pravna_informatika.projekat.model.CaseDescription;
import org.springframework.stereotype.Service;


import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class DrDeviceService {

    private final Path drDevicePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "dr-device-files");


    public String start(CaseDescription caseDescription) {
        writeFacts(caseDescription);
        Path scriptPath = Paths.get(drDevicePath.toString(), "start.bat");
        runScriptFromDirectory(scriptPath, drDevicePath);
        return (readExport());
    }


    public void clean() {
        Path scriptPath = Paths.get(drDevicePath.toString(), "clean.bat");
        runScriptFromDirectory(scriptPath, drDevicePath);
    }

    public static void runScriptFromDirectory(Path scriptPath, Path directoryPath) {
//        ProcessBuilder pb = new ProcessBuilder(scriptPath.toString());
//        pb.directory(directoryPath.toFile());
        String file = scriptPath.getFileName().toString();
        System.out.println(file);
        String absPath = "./src/main/resources/dr-device-files/" + file;
        System.out.println(absPath);
        Runtime runtime = Runtime.getRuntime();

        try {
            System.out.println("Izlaz iz terminala:");
            Process p1 = runtime.exec("ls");
            //Process p1 = runtime.exec("cmd /c start " + absPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("Izlaz zavrsen");
//            runProcess(p1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runProcess(Process pb) throws IOException {
//        pb.redirectErrorStream(true);
//        Process p = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    private void writeFacts(CaseDescription caseDescription) {
        File f = Paths.get(drDevicePath.toString(), "facts.rdf").toFile();
        try(FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
                    "        xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" +
                    "        xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" +
                    "        xmlns:lc=\"http://informatika.ftn.uns.ac.rs/legal-case.rdf#\">\n" +
                    "    <lc:case rdf:about=\"http://informatika.ftn.uns.ac.rs/legal-case.rdf#case01\">\n" +
                    "        <lc:name>case 01</lc:name>\n" +
                    "        <lc:prodaje>" + transform(caseDescription.getProdaje()) + "</lc:prodaje>\n" +
                    "        <lc:proizvodi>" + transform(caseDescription.getProizvodi()) + "</lc:proizvodi>\n" +
                    "        <lc:posreduje>" + transform(caseDescription.getPosreduje()) + "</lc:posreduje>\n" +
                    "        <lc:poseduje>" + transform(caseDescription.getPoseduje()) + "</lc:poseduje>\n" +
                    "        <lc:usao_u_zemlju>" + transform(caseDescription.getUsao_u_zemlju()) + "</lc:usao_u_zemlju>\n" +
                    "        <lc:organizuje_mrezu>" + transform(caseDescription.getOrganizuje_mrezu()) + "</lc:organizuje_mrezu>\n" +
                    "        <lc:prodaje_ugrozenim>" + transform(caseDescription.getProdaje_ugrozenim()) + "</lc:prodaje_ugrozenim>\n" +
                    "        <lc:is_sluzbeno_lice>" + transform(caseDescription.getIs_sluzbeno_lice()) + "</lc:is_sluzbeno_lice>\n" +
                    "        <lc:drukao>" + transform(caseDescription.getDrukao()) + "</lc:drukao>\n" +
                    "        <lc:obezbedjuje_materijal>" + transform(caseDescription.getObezbedjuje_materijal()) + "</lc:obezbedjuje_materijal>\n" +
                    "        <lc:obezbedjuje_supstancu>" + transform(caseDescription.getObezbedjuje_supstancu()) + "</lc:obezbedjuje_supstancu>\n" +
                    "        <lc:prisustvo_supstanci>" + transform(caseDescription.getPrisustvo_supstanci()) + "</lc:prisustvo_supstanci>\n" +
                    "    </lc:case>\n" +
                    "</rdf:RDF>";
            out.print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String transform(String s) {
        return s.equals("da") ? "yes" : "no";
    }



    private String readExport() {
        StringBuilder ret = new StringBuilder();
        File f = Paths.get(drDevicePath.toString(), "facts.rdf").toFile();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(f);
            var childNodes = document.getChildNodes();
            Node n = document.getChildNodes().item(0);
            NodeList nodeList = n.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                if (node.getNodeName().contains("facts") && node.getTextContent().contains("defeasibly-proven-positive")) {
                    String nodeName = node.getNodeName().split(":")[1];
                    String childNodeName = node.getChildNodes().item(1).getNodeName().split(":")[1];
                    String childNodeText = node.getChildNodes().item(1).getTextContent();
                    ret.append(nodeName).append(": ").append(childNodeName).append("=").append(childNodeText).append(", ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(ret.toString());

        if (ret.length() < 2)
            return "Nema informacija o primenjenim zakonskim odredbama.";

        ret.setLength(ret.length() - 2);
        return ret.toString();
    }
}
