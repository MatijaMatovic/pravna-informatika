package com.pravna_informatika.projekat.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class CaseDescriptionDTO {
    public String sud;
    public String poslovniBroj; //brojSlucaja
    public String sudija; //judge
    public String okrivljeni; //defendant
    public String krivicnoDelo; // getFelony
    public String supstanca; //substance
    public String zapisnicar;
    public String prodaje;
    public String proizvodi;
    public String poseduje;
    public String posreduje;
    public String usao_u_zemlju;
    public String organizuje_mrezu;
    public String prodaje_ugrozenim;
    public String is_sluzbeno_lice;
    public String drukao;
    public String obezbedjuje_materijal;
    public String obezbedjuje_supstancu;
    public String prisustvo_supstanci;
    public String kazna;

    public CaseDescriptionDTO(String sud, String poslovniBroj, String sudija, String okrivljeni, String krivicnoDelo, String supstanca, String zapisnicar, String prodaje, String proizvodi, String poseduje, String posreduje, String usao_u_zemlju, String organizuje_mrezu, String prodaje_ugrozenim, String is_sluzbeno_lice, String drukao, String obezbedjuje_materijal, String obezbedjuje_supstancu, String prisustvo_supstanci, String kazna) {
        this.sud = sud;
        this.poslovniBroj = poslovniBroj;
        this.sudija = sudija;
        this.okrivljeni = okrivljeni;
        this.krivicnoDelo = krivicnoDelo;
        this.supstanca = supstanca;
        this.zapisnicar = zapisnicar;
        this.prodaje = prodaje;
        this.proizvodi = proizvodi;
        this.poseduje = poseduje;
        this.posreduje = posreduje;
        this.usao_u_zemlju = usao_u_zemlju;
        this.organizuje_mrezu = organizuje_mrezu;
        this.prodaje_ugrozenim = prodaje_ugrozenim;
        this.is_sluzbeno_lice = is_sluzbeno_lice;
        this.drukao = drukao;
        this.obezbedjuje_materijal = obezbedjuje_materijal;
        this.obezbedjuje_supstancu = obezbedjuje_supstancu;
        this.prisustvo_supstanci = prisustvo_supstanci;
        this.kazna = kazna;
    }

    public static CaseDescriptionDTO fromCaseDescription(CaseDescription other) {
        return new CaseDescriptionDTO(
            other.getSud(), //brojSl
            other.getPoslovniBroj(), //judge
            other.getSudija(), //defendan
            other.getOkrivljeni(), // getFe
            other.getKrivicnoDelo(), //substance
            other.getSupstanca(),
            other.getZapisnicar(),
            other.getProdaje(),
            other.getProizvodi(),
            other.getPoseduje(),
            other.getPosreduje(),
            other.getUsao_u_zemlju(),
            other.getOrganizuje_mrezu(),
            other.getProdaje_ugrozenim(),
            other.getIs_sluzbeno_lice(),
            other.getDrukao(),
            other.getObezbedjuje_materijal(),
            other.getObezbedjuje_supstancu(),
            other.getPrisustvo_supstanci(),
            other.getKazna());
    }


    public String getSud() {
        return sud;
    }

    public void setSud(String sud) {
        this.sud = sud;
    }

    public String getPoslovniBroj() {
        return poslovniBroj;
    }

    public void setPoslovniBroj(String poslovniBroj) {
        this.poslovniBroj = poslovniBroj;
    }

    public String getSudija() {
        return sudija;
    }

    public void setSudija(String sudija) {
        this.sudija = sudija;
    }

    public String getOkrivljeni() {
        return okrivljeni;
    }

    public void setOkrivljeni(String okrivljeni) {
        this.okrivljeni = okrivljeni;
    }

    public String getKrivicnoDelo() {
        return krivicnoDelo;
    }

    public void setKrivicnoDelo(String krivicnoDelo) {
        this.krivicnoDelo = krivicnoDelo;
    }

    public String getSupstanca() {
        return supstanca;
    }

    public void setSupstanca(String supstanca) {
        this.supstanca = supstanca;
    }

    public String getZapisnicar() {
        return zapisnicar;
    }

    public void setZapisnicar(String zapisnicar) {
        this.zapisnicar = zapisnicar;
    }

    public String getProdaje() {
        return prodaje;
    }

    public void setProdaje(String prodaje) {
        this.prodaje = prodaje;
    }

    public String getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(String proizvodi) {
        this.proizvodi = proizvodi;
    }

    public String getPoseduje() {
        return poseduje;
    }

    public void setPoseduje(String poseduje) {
        this.poseduje = poseduje;
    }

    public String getPosreduje() {
        return posreduje;
    }

    public void setPosreduje(String posreduje) {
        this.posreduje = posreduje;
    }

    public String getUsao_u_zemlju() {
        return usao_u_zemlju;
    }

    public void setUsao_u_zemlju(String usao_u_zemlju) {
        this.usao_u_zemlju = usao_u_zemlju;
    }

    public String getOrganizuje_mrezu() {
        return organizuje_mrezu;
    }

    public void setOrganizuje_mrezu(String organizuje_mrezu) {
        this.organizuje_mrezu = organizuje_mrezu;
    }

    public String getProdaje_ugrozenim() {
        return prodaje_ugrozenim;
    }

    public void setProdaje_ugrozenim(String prodaje_ugrozenim) {
        this.prodaje_ugrozenim = prodaje_ugrozenim;
    }

    public String getIs_sluzbeno_lice() {
        return is_sluzbeno_lice;
    }

    public void setIs_sluzbeno_lice(String is_sluzbeno_lice) {
        this.is_sluzbeno_lice = is_sluzbeno_lice;
    }

    public String getDrukao() {
        return drukao;
    }

    public void setDrukao(String drukao) {
        this.drukao = drukao;
    }

    public String getObezbedjuje_materijal() {
        return obezbedjuje_materijal;
    }

    public void setObezbedjuje_materijal(String obezbedjuje_materijal) {
        this.obezbedjuje_materijal = obezbedjuje_materijal;
    }

    public String getObezbedjuje_supstancu() {
        return obezbedjuje_supstancu;
    }

    public void setObezbedjuje_supstancu(String obezbedjuje_supstancu) {
        this.obezbedjuje_supstancu = obezbedjuje_supstancu;
    }

    public String getPrisustvo_supstanci() {
        return prisustvo_supstanci;
    }

    public void setPrisustvo_supstanci(String prisustvo_supstanci) {
        this.prisustvo_supstanci = prisustvo_supstanci;
    }

    public String getKazna() {
        return kazna;
    }

    public void setKazna(String kazna) {
        this.kazna = kazna;
    }

}
