package com.skax.eatool.cashCard.business.cashCardRule.helper;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * IOBoundCardRegister Implementation
 * 
 * Implementation of card registration operations
 */
@Component
public class IOBoundCardRegisterImpl implements IOBoundCardRegister {

    private static final Logger logger = LoggerFactory.getLogger(IOBoundCardRegisterImpl.class);

    // file ?뺣낫
    private String CardFPath = "/home/ftpuser/CARD/";
    private String CardFName = "CardRegister";

    // track #1
    private String SS = "%";
    private String FC = "B";
    private String PAN;
    private String FS1 = "^";
    private String Name;
    private String FS2 = "^";
    private String RegionCode = "50";
    private String BankKind = "5";
    private String SerialNo1 = "03";
    private String serialNo2 = "013";
    private String BankCode = "03";
    private String BranchCode;
    private String CardNo;
    private String ES = "?";

    private int PAN_Len = 16;
    private int Name_Len = 26;
    private int BranchCode_Len = 4;
    private int CardNo_Len = 16;

    // track #2
    private String SS2 = ";";
    private String PAN2;
    private String FS3 = "=";
    private String CardNo2;
    private String ES2 = "?";

    public void setAccountNo(String acctno) {
        logger.debug("==================[IOBoundCardRegisterImpl.setAccountNo START] - acctno: {}", acctno);
        try {
            PAN = "00000" + acctno;
            PAN2 = "00000" + acctno;
            logger.debug("==================[IOBoundCardRegisterImpl.setAccountNo END] - acctno: {}", acctno);
        } catch (Exception e) {
            logger.error("==================[IOBoundCardRegisterImpl.setAccountNo ERROR] - acctno: {}, 에러: {}", acctno,
                    e.getMessage(), e);
            throw e;
        }
    }

    public void setName(String name) {
        logger.debug("==================[IOBoundCardRegisterImpl.setName START] - name: {}", name);
        try {
            Name = PSpaceToStr(name, Name_Len);
            logger.debug("==================[IOBoundCardRegisterImpl.setName END] - name: {}", name);
        } catch (Exception e) {
            logger.error("==================[IOBoundCardRegisterImpl.setName ERROR] - name: {}, 에러: {}", name,
                    e.getMessage(), e);
            throw e;
        }
    }

    public void setBranchCode(String branch) {
        logger.debug("==================[IOBoundCardRegisterImpl.setBranchCode START] - branch: {}", branch);
        try {
            BranchCode = branch;
            if (BranchCode.equals("0238")) {
                serialNo2 = "013";
                RegionCode = "50";
            }
            if (BranchCode.equals("0239")) {
                serialNo2 = "018";
                RegionCode = "10";
            }
            logger.debug("==================[IOBoundCardRegisterImpl.setBranchCode END] - branch: {}", branch);
        } catch (Exception e) {
            logger.error("==================[IOBoundCardRegisterImpl.setBranchCode ERROR] - branch: {}, 에러: {}", branch,
                    e.getMessage(), e);
            throw e;
        }
    }

    public void setCardNo(String cardno) {
        logger.debug("==================[IOBoundCardRegisterImpl.setCardNo START] - cardno: {}", cardno);
        try {
            CardNo = cardno;
            CardNo2 = CardNo;
            logger.debug("==================[IOBoundCardRegisterImpl.setCardNo END] - cardno: {}", cardno);
        } catch (Exception e) {
            logger.error("==================[IOBoundCardRegisterImpl.setCardNo ERROR] - cardno: {}, 에러: {}", cardno,
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public synchronized void execute(String fname) {
        logger.info("==================[IOBoundCardRegisterImpl.execute START] - fname: {}", fname);
        String name = null, acctno = null, branch = null, cardno = null;

        try {
            BufferedReader ipin = new BufferedReader(new FileReader(fname));

            for (String ipline; (ipline = ipin.readLine()) != null;) {
                if (ipline.equals("")) {
                    continue;
                }
                if (ipline.substring(0, 1).equals("#")) {
                    continue;
                }
                String delim = ",";
                StringTokenizer tokenizer1 = new StringTokenizer(ipline, delim);
                if (tokenizer1.countTokens() != 4) {
                    logger.debug("Number of Tokens: " + tokenizer1.countTokens());
                    logger.debug("[" + ipline + "]");
                    continue;
                }
                int i = 0;
                while (tokenizer1.hasMoreTokens()) {
                    String var = tokenizer1.nextToken();
                    switch (i) {
                        case 0:
                            setAccountNo(var);
                            break;
                        case 1:
                            setName(var);
                            break;
                        case 2:
                            setBranchCode(var);
                            break;
                        case 3:
                            setCardNo(var);
                            break;
                    }
                    i++;
                }
                // Simple delay without InterruptedException
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    logger.warn("Thread sleep interrupted: {}", e.getMessage());
                    Thread.currentThread().interrupt();
                }

                String CardSeq = getCardSeq();
                this.makefile(null, CardSeq + this.CardNo + Track1_toString() + Track2_toString());
                logger.debug(CardSeq + this.CardNo + Track1_toString() + Track2_toString());
                setCardSeq(CardSeq, GetSysDate());
            }
            ipin.close();
            logger.info("==================[IOBoundCardRegisterImpl.execute END] - fname: {}", fname);
        } catch (IOException e) {
            logger.error("==================[IOBoundCardRegisterImpl.execute ERROR] - fname: {}, IOException: {}",
                    fname, e.getMessage(), e);
            throw new RuntimeException("Failed to execute card registration from file", e);
        } catch (Exception ex) {
            logger.error("==================[IOBoundCardRegisterImpl.execute ERROR] - fname: {}, 에러: {}", fname,
                    ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public synchronized void execute(String acctno, String name, String branch, String cardno) {
        logger.info(
                "==================[IOBoundCardRegisterImpl.execute START] - acctno: {}, name: {}, branch: {}, cardno: {}",
                acctno, name, branch, cardno);
        try {
            setName(name);
            setAccountNo(acctno);
            setBranchCode(branch);
            setCardNo(cardno);

            if (BranchCode.equals("0238")) {
            } else if (BranchCode.equals("0239")) {
            } else {
                logger.debug("-------------- branchcode not match");
                return;
            }

            String CardSeq = getCardSeq();
            this.makefile(null, CardSeq + this.CardNo + Track1_toString() + Track2_toString());
            setCardSeq(CardSeq, GetSysDate());
            logger.info(
                    "==================[IOBoundCardRegisterImpl.execute END] - acctno: {}, name: {}, branch: {}, cardno: {}",
                    acctno, name, branch, cardno);
        } catch (Exception e) {
            logger.error(
                    "==================[IOBoundCardRegisterImpl.execute ERROR] - acctno: {}, name: {}, branch: {}, cardno: {}, 에러: {}",
                    acctno, name, branch, cardno, e.getMessage(), e);
            throw e;
        }
    }

    public String Track2_toString() {
        return (SS2 +
                PAN2 +
                FS3 +
                CardNo2 +
                ES2);
    }

    public String Track1_toString() {
        return (SS +
                FC +
                PAN +
                FS1 +
                Name +
                FS2 +
                RegionCode +
                BankKind +
                SerialNo1 +
                serialNo2 +
                BankCode +
                BranchCode +
                CardNo +
                ES);
    }

    public final String PSpaceToStr(String str1, int size) {
        if (str1 == null)
            str1 = " ";

        String str = null;
        if (str1.length() >= size) {
            str = str1.substring(0, size);
        } else
            str = str1;

        StringBuilder tt = new StringBuilder();
        for (int i = 1; i <= size; i++) {
            tt.append(' ');
        }
        for (int i = 0; i < str.length(); i++) {
            tt.setCharAt(i, str.charAt(i));
        }

        return tt.toString();
    }

    public synchronized void makefile(String cardno, String contentToWrite) {
        FileOutputStream fos = null;
        PrintStream ps = null;

        try {
            if (!DIRmake(this.CardFPath)) {
                logger.debug("Card File Make error");
                return;
            }

            if (!DIRmake(this.CardFPath + GetSysDate())) {
                logger.debug("Card File Make error");
                return;
            }

            String CardFName = this.CardFPath + GetSysDate() + "/" + this.GetHostName() + "." + this.CardFName + "."
                    + GetSysDate();

            fos = new FileOutputStream(CardFName, true);
            ps = new PrintStream(fos);
            ps.println(contentToWrite);
            ps.flush();
            ps.close();
            fos.close();
        } catch (Exception e) {
            try {
                if (fos != null)
                    fos.close();
                if (ps != null)
                    ps.close();
            } catch (Exception ex) {
            }
            logger.error("Error making card file", e);
        }

        Fmode(CardFName);
    }

    public synchronized void makeenv() {
        String CardFName = null;
        FileOutputStream fos = null;
        PrintStream ps = null;

        try {
            CardFName = "/home/ftpuser/CARD/SEQ/CardSeq.INI";
            File f = new File(CardFName);

            if (f.exists()) {
                return;
            }
            fos = new FileOutputStream(CardFName, true);
            ps = new PrintStream(fos);
            ps.println("LSeq=00000000");
            ps.println("LDate=" + this.GetSysDate());
            ps.flush();
            ps.close();
            fos.close();
        } catch (Exception e) {
            try {
                if (fos != null)
                    fos.close();
                if (ps != null)
                    ps.close();
            } catch (Exception ex) {
            }
            logger.error("Error making environment file", e);
        }
    }

    public static String GetHostName() {
        InetAddress Address = null;
        try {
            Address = InetAddress.getLocalHost();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return (null);
        }
        return (Address.getHostName());
    }

    public static String GetSysDate() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(new Date()).toString();
    }

    public static String GetSysTime() {
        SimpleDateFormat fmt = new SimpleDateFormat("HHmmssSSS");
        return fmt.format(new java.util.Date()).toString();
    }

    public void Fmode(String FName) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("/usr/bin/chmod 777 " + FName);
        } catch (IOException ex) {
            logger.error("Error setting file mode: " + FName, ex);
        }
    }

    public final boolean DIRmake(String FName) {
        try {
            File f = new File(FName);

            if (f.exists()) {
                return true;
            }
            f.mkdirs();
            if (!f.isDirectory())
                throw new RuntimeException(FName + "DIR does not make dir");
        } catch (Exception ex) {
            logger.debug("DIRmake Exception :: " + ex.getMessage());
            logger.debug("DIRmake FileName  :: " + FName);
            return false;
        }
        return true;
    }

    public synchronized String getCardSeq() {
        String FName = "/home/ftpuser/CARD/SEQ/CardSeq.INI";
        String LSeq = null;
        String LDate = null;
        try {
            makeenv();
            Properties p = new Properties();
            p.load(new FileInputStream(FName));
            LSeq = p.getProperty("LSeq");
            LDate = p.getProperty("LDate");
            if (LDate.equals(GetSysDate())) {
                int iseq = Integer.valueOf(LSeq).intValue() + 1;
                DecimalFormat fmt = new DecimalFormat("00000000");
                fmt = new DecimalFormat("00000000");
                String sseq = fmt.format(iseq);
                return sseq;
            } else {
                setCardSeq("00000001", GetSysDate());
                return "00000001";
            }
        } catch (IOException e) {
            logger.error("Error getting card sequence - IOException: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get card sequence", e);
        } catch (Exception e) {
            logger.error("Error getting card sequence", e);
            return null;
        }
    }

    public synchronized boolean setCardSeq(String seq, String date) {
        String FName = "/home/ftpuser/CARD/SEQ/CardSeq.INI";
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(FName));
            p.setProperty("LSeq", seq);
            p.setProperty("LDate", date);
            p.store(new FileOutputStream(FName), "INI REFERENCE[CardSeq.INI]");
        } catch (IOException e) {
            logger.error("Error setting card sequence - IOException: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to set card sequence", e);
        } catch (Exception e) {
            logger.error("Error setting card sequence", e);
            return false;
        }
        return true;
    }
}
