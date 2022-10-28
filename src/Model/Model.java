/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.swing.JOptionPane;

/**
 *
 * @author pwn233
 */
public class Model {
    private double salary, cryptoProfit;
    private int month;
    private double personalDiscount, socialInsurance, lifeInsurance, healthInsurance, rmf, ssf, pvd;
    
    //max discount
    double maxLI = 100000.00;
    double maxHI = 25000.00;
    double maxFund = 500000.00;
    double maxRMF = 500000.00;
    double rateRMF = 0.30;
    double maxSSF = 200000.00;
    double rateSSF = 0.30;
    double maxPVD = 500000.00;
    double ratePVD = 0.15;
    double maxInsurance = maxHI+maxLI;
        
    private double discount, vat, netTemp, net;
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getCryptoProfit() {
        return cryptoProfit;
    }

    public void setCryptoProfit(double cryptoProfit) {
        this.cryptoProfit = cryptoProfit;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getPersonalDiscount() {
        return personalDiscount;
    }

    public void setPersonalDiscount(double personalDiscount) {
        this.personalDiscount = personalDiscount;
    }

    public double getSocialInsurance() {
        return socialInsurance;
    }

    public void setSocialInsurance(double socialInsurance) {
        this.socialInsurance = socialInsurance;
    }

    public double getLifeInsurance() {
        return lifeInsurance;
    }

    public void setLifeInsurance(double lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public double getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(double healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public double getRmf() {
        return rmf;
    }

    public void setRmf(double rmf) {
        this.rmf = rmf;
    }

    public double getSsf() {
        return ssf;
    }

    public void setSsf(double ssf) {
        this.ssf = ssf;
    }

    public double getPvd() {
        return pvd;
    }

    public void setPvd(double pvd) {
        this.pvd = pvd;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getNet() {
        return net;
    }

    public void setNet(double net) {
        this.net = net;
    }
    
    public void discountCal() {
        discount = personalDiscount + socialInsurance + lifeInsurance + healthInsurance + rmf + ssf + pvd;
    }
    
    public String checkDiscount() {
        String status = "";
        if(socialInsurance > 5100) {
            status = "Fail : Social Insurance can not be over 5,100.";
        }
        if(lifeInsurance+healthInsurance > maxInsurance) {
            status = "Fail : Overall Insurance cannot be over 100,000";
        } else {
            if(healthInsurance > maxHI) {
               status = "Fail : Health Insurance cannot be over 25,000"; 
            } else if(lifeInsurance > maxLI) {
               status = "Fail : Life Insurance cannot be over 100,000";
            }
        }
        if(rmf+ssf+pvd > maxFund) {
            status = "Fail : Overall Fund cannot be over 500,000";
        } else {
            if(rmf > salary*rateRMF || rmf > maxRMF) {
                status = "Fail : RMF cannot be over 500,000";
            } else if (ssf > salary*rateSSF || ssf > maxSSF) {
                status = "Fail : SSF cannot be over 500,000";
            } else if (pvd > salary*ratePVD || pvd > maxPVD) {
                status = "Fail : PVD cannot be over 500,000";
            }
        }
        return status;
    }
    
    public void vatLevel() {
        double[] amount = {150000.00, 300000.00, 500000.00, 750000.00, 1000000.00, 2000000.00, 5000000.00};
        double[] vatTier = {0.00, 0.05, 0.10, 0.15, 0.20, 0.25, 0.30, 0.35};
        double[] extraCost = {7500.00, 27500.00, 65000.00, 115000.00, 365000.00, 1265000.00};
        if(net <= amount[0]) {
            vat = vatTier[0];
        } else if (net <= amount[1]) {
            vat = (net - amount[0]) * vatTier[1];
        } else if (net <= amount[2]) {
            vat = ((net - amount[1]) * vatTier[2]) + extraCost[0];
        } else if (net <= amount[3]) {
            vat = ((net - amount[2]) * vatTier[3]) + extraCost[1];
        } else if (net <= amount[4]) {
            vat = ((net - amount[3]) * vatTier[4]) + extraCost[2];
        } else if (net <= amount[5]) {
            vat = ((net - amount[4]) * vatTier[5]) + extraCost[3];
        } else if (net <= amount[6]) {
            vat = ((net - amount[5]) * vatTier[6]) + extraCost[4];
        } else if (net > amount[6]) {
            vat = ((net - amount[6]) * vatTier[7]) + extraCost[5];
        }
    }
    
    public void summarize() {
        double disOverall = 100000.00;
        // case less 100000 and 50% net salary
        if(discount <= (salary*month)/2 && discount <= disOverall) {
            netTemp = (salary*month) + cryptoProfit - discount;
        } else {
        // case more 100000 and 50% net salary
            netTemp = (salary*month) + cryptoProfit - disOverall;
        }
    }
    
    public void finalNet() {
        net = netTemp - vat;
    }
    public String insertDatabase() {
        String status = "", query = "";
        query = String.format("INSERT INTO account_data (id, salary, month, crypto_profit, discount, vat, net)VALUES (NULL, '%1$f', '%2$d', '%3$f', '%4$f', '%5$f', '%6$f')", salary, month, cryptoProfit, discount, vat ,net);
            try {
                DBConnect conn = new DBConnect();
                boolean result = conn.execute(query);
                if (result) {
                    status = "complete";
                } else {
                    status = "fail";
                }
                conn.close();
            } catch (Exception ex) {
                status = "Error Update: " + ex;
            }
        return status;
    }
    
}
