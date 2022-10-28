/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Model;

/**
 *
 * @author pwn233
 */
public class Control {
    private Model m = new Model();
    public void setData(double salary, int month, double cp) {
        m.setSalary(salary);
        m.setMonth(month);
        m.setCryptoProfit(cp);
    }
    
    public String returnStatus() {
        System.out.println(m.insertDatabase());
        return m.insertDatabase();
    }

    public void calculate() {
        m.discountCal();
        m.summarize();
        m.vatLevel();
        m.finalNet();
        
    }

    public void setDiscount(double personalDiscount, double socialInsurance, double lifeInsurance, double healthInsurance, double rmf, double ssf, double pvd) {
        m.setPersonalDiscount(personalDiscount);
        m.setSocialInsurance(socialInsurance);
        m.setLifeInsurance(lifeInsurance);
        m.setHealthInsurance(healthInsurance);
        m.setRmf(rmf);
        m.setSsf(ssf);
        m.setPvd(pvd);
    }

    public String checkDiscount() {
        return m.checkDiscount();
    }
}
