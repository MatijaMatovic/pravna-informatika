package com.pravna_informatika.projekat.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final ResourceLoader resourceLoader;

    public String getText(String text_path) throws IOException {
        //Resource resource = resourceLoader.getResource(
        //        text_path
        //);
        //Path path = Paths.get(resource.getURI());
        File file = new File(text_path);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    public String getCaseNumber(String verdict_text) {
        Pattern pattern = Pattern.compile("^[kK]\\.\\s?([Bb]r\\.\\s?)?[0-9]{2}/[0-9]{1,4}\\s");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = " ";
        if (matcher.find()) {
            ret = matcher.group();
        } else {
            Pattern pattern2 = Pattern.compile("\\s[kK]\\.\\s?([Bb]r\\.\\s?)?[0-9]{2}/[0-9]{1,4}\\s");
            Matcher matcher2 = pattern2.matcher(verdict_text);
            if (matcher2.find()) {
                ret = matcher2.group();
            }
        }
        return ret.trim();
    }


    public String getSud(String verdict_text) {
        Pattern pattern = Pattern.compile("\\sU IME CRNE GORE\\s*[A-ZŽĐŠČĆa-zžđšćčć]+ ((SUD U)|(sud u)) [A-ZŽĐŠČĆa-zžđšćčć]+");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = "unknown";
        if (matcher.find()) {
            ret = matcher.group();

            Pattern pattern2 = Pattern.compile("\\s[A-ZŽĐŠČĆa-zžđšćčć]+ ((SUD U)|(sud u)) [A-ZŽĐŠČĆa-zžđšćčć]+");
            Matcher matcher2 = pattern2.matcher(ret);
            if (matcher2.find()) {
                ret = matcher2.group();
            }
        }
        return ret.trim();
    }

    public String getDefendantInitials(String verdict_text) {
        Pattern pattern = Pattern.compile("\\s((PRESUDU)|(P R E S U D U))\\s*((Okrivljen[ai])|(OKRIVLJEN[AI])|(Optužen[ia])):?\\s*[A-ZŽĐŠČĆ]{1,2}([., ]) ?[A-ZŽĐŠČĆ]{1,2}([., ])");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = "unknown";
        if (matcher.find()) {
            ret = matcher.group();
            if (ret.contains("P R E S U D U"))
                ret = ret.split("P R E S U D U")[1];
            Pattern pattern2 = Pattern.compile("\\s[A-ZŽĐŠČĆ]{1,2}([., ]) ?[A-ZŽĐŠČĆ]{1,2}([., ])");
            Matcher matcher2 = pattern2.matcher(ret);
            if (matcher2.find()) {
                ret = matcher2.group();
            }
        }
        return ret.replace(",", ".").replace(". ", ".").replace(".", ". ").trim();
    }

    public String getJudge(String verdict_text) throws IOException {
        Pattern pattern = Pattern.compile("\\s((ZAPISNIČAR(KA)?)|(Zapisničar(ka)?))(,|\\s)?\\s*((SUDIJA)|(S U D I J A)|(SUTKINJA)|(S U T K I N J A)),?\\s+[A-ZŽĐŠČĆa-zžđšćčć]+ [A-ZŽĐŠČĆa-zžđšćčć]+((,\\s?s.r.)|,|\\s)\\s?[A-ZŽĐŠČĆa-zžđšćčć]+ [A-ZŽĐŠČĆa-zžđšćčć]+,?(\\s?s.r.)?");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = "unknown";
        if (matcher.find()) {
            ret = matcher.group();
            String lines[] = ret.split("\\r?\\n");
            ret = lines[2];
            String names[] = ret.split(",");
            if (names.length > 1)
                ret = names[1].replace("s.r.", "").trim();
            else {
                names = ret.split(" ");
                ret = names[2] + " " + names[3];
            }
        } else {
            Pattern pattern2 = Pattern.compile("\\s((Sudija)|(SUDIJA)|(S U D I J A)|(Sutkinja)|(SUTKINJA)|(S U T K I N J A))(:|,)?\\s+(Mr )?[A-ZŽĐŠČĆa-zžđšćčć]+ [A-ZŽĐŠČĆa-zžđšćčć]+");
            Matcher matcher2 = pattern2.matcher(verdict_text);
            if (matcher2.find()) {
                ret = matcher2.group();

                String lines[] = ret.split("\\r?\\n");
                int i = 0;
                do {
                    i++;
                    ret = lines[i].replace("s.r.", "").trim();

                } while (lines[i].matches("^\\s*$"));
            }
        }
        return ret;
    }

    public String getClerk(String verdict_text) throws IOException {
        Pattern pattern = Pattern.compile("\\s((ZAPISNIČAR(KA)?)|(Zapisničar(ka)?))(,|\\s)?\\s*((SUDIJA)|(S U D I J A)|(SUTKINJA)|(S U T K I N J A)),?\\s+[A-ZŽĐŠČĆa-zžđšćčć]+ [A-ZŽĐŠČĆa-zžđšćčć]+");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = "unknown";
        if (matcher.find()) {
            ret = matcher.group();
            String lines[] = ret.split("\\r?\\n");
            ret = lines[2];
            return ret.replace("s.r.", "").trim();
        }
        Pattern pattern2 = Pattern.compile("\\sZTO(:|-)\\s?[A-ZŽĐŠČĆa-zžđšćčć]+ [A-ZŽĐŠČĆa-zžđšćčć]+");
        Matcher matcher2 = pattern2.matcher(verdict_text);

        //Pattern pattern3 = Pattern.compile("\\s((uz\\sučešće(\\snamještenika\\ssuda)?)|(sa\\szapisničarom))\\s+[A-ZŽĐŠČĆa-zžđšćčć]+ [A-ZŽĐŠČĆa-zžđšćčć]+");
        if (matcher2.find())
            return matcher2.group().replace("ZTO:", "").replace("ZTO-", "").trim();
        Pattern pattern3 = Pattern.compile("\\s((uz učešće\\s([a-zžđšćčć]+\\s){0,5})|(sa zapisničarom\\s))\\s*[A-ZŽĐŠČĆ]{1,2}[a-zžđšćčć]+ [A-ZŽĐŠČĆ]{1,2}[a-zžđšćčć]+");
        Matcher matcher3 = pattern3.matcher(verdict_text);
        if (matcher3.find()) {
            ret = matcher3.group().replace(",", "").replace("\r\n", " ").replace("\n", " ").trim();
        }
        return ret;
    }

    public List<String> getFelony(String verdict_text) throws IOException {
        Pattern pattern = Pattern.compile("\\s+zbog\\s+krivičnog\\s+djela\\s+[A-ZŽĐŠČĆa-zžđšćčć0-9.,\\s]*?\\s+Krivičnog\\s+zakonika\\s+Crne\\s+Gore");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = "unknown";
        List<String> results = new ArrayList<>();
        if (matcher.find()) {
            ret = matcher.group();
            results.add(ret);
        }
        else {
            Pattern pattern2 = Pattern.compile("(\\szbog)?\\skrivičnog\\sdjela\\s[A-ZŽĐŠČĆa-zžđšćčć0-9.,\\s]*?\\sKrivičnog\\szakonika\\sCrne\\sGore");
            Matcher matcher2 = pattern2.matcher(verdict_text);
            while (matcher2.find()) {
                if (matcher2.groupCount() > 0) {
                    results.add(matcher2.group(1));
                } else {
                    results.add(matcher2.group());
                }
            }
        }
        results = results.stream().map(val -> {
            if (val == null) return "";
            return val.replace("\r\n", " ").replace("\n", " ").trim();
        }).toList();
        return results;
    }

    public String getVerdict(String verdict_text) throws IOException {
        Pattern pattern = Pattern.compile("\\s((U ?S ?L ?O ?V ?N ?U ? O ?S ?U ?D ?U)|(O ?S ?U ?Đ ?U ?J ?E))\\s+[\\S\\s]*?\\.\r?\n");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = "unknown";
        if (matcher.find()) {
            ret = matcher.group();
        }
        return ret.replace("\r\n", " ").replace("\n", " ").trim();
    }

    //radi za sve sem 187 i 504
    public String getSubstance(String verdict_text) throws IOException {
        Pattern pattern = Pattern.compile("\\s((MJER[UA] BEZBJEDNOSTI))\\s+[A-ZŽĐŠČĆA-ZŽĐŠČĆa-zžđšćčć,0-9()/:.'„“”–€\"\\s]*?\\.\r?\n");
        Matcher matcher = pattern.matcher(verdict_text);
        String ret = "unknown";
        if (matcher.find()) {
            ret = matcher.group();
            if(ret.contains("ODUZIMANJE PREDMETA")){
                String[] parts = ret.split("ODUZIMANJE PREDMETA");
                ret = parts[1];
            }
            if(ret.contains("ODUZIMA SE")){
                String[] parts = ret.split("ODUZIMA SE");
                ret = parts[1];
            }
            if (ret.contains(", a koja")) {
                ret = ret.split(", a koja")[0];
            }

        }
        return ret.replace("\r\n", " ").replace("\n", " ").trim();
    }


    public List<String> getMetadata(String file) throws IOException {
        String verdict_text = getText(file);
        List<String> metadata = new ArrayList<>();
        int file_path_size = file.split("/").length;
        metadata.add(file.split("/")[file_path_size - 1].split("\\.")[0]);
        metadata.add(getJudge(verdict_text));
        metadata.add(getSubstance(verdict_text));
        metadata.add(getClerk(verdict_text));
        metadata.add(getDefendantInitials(verdict_text));
        metadata.add(getSud(verdict_text));
        metadata.add(getVerdict(verdict_text));
        List<String> res = getFelony(verdict_text);
        if (!res.isEmpty())
            metadata.add(res.get(0));
        System.out.println(metadata);
        return metadata;


    }


    public void run(){
        try {
            FileWriter myWriter = new FileWriter("metadata.txt");
            try (Stream<Path> paths = Files.walk(Paths.get("/home/matija/programi/Pravna informatika/Nas projekat/projekat/backend/src/main/resources/static/Presude_pdf/"))) {
                paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            List<String> metadata = getMetadata(String.valueOf(file));
                            try {
                                metadata.stream().forEach(var -> {
                                    try {
                                        myWriter.write(var + ";");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                myWriter.write('\n');

                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
