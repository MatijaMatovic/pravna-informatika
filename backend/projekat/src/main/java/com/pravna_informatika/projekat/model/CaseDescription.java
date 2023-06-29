package com.pravna_informatika.projekat.model;
//import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
//import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class CaseDescription implements CaseComponent {

    private int id;
    private String sud;
    private String poslovniBroj; //brojSlucaja
    private String sudija; //judge
    private String okrivljeni; //defendant
    private String krivicnoDelo; // getFelony
    private String supstanca; //substance
    private String zapisnicar;
    private String prodaje;
    private String proizvodi;
    private String poseduje;
    private String posreduje;
    private String usao_u_zemlju;
    private String organizuje_mrezu;
    private String prodaje_ugrozenim;
    private String is_sluzbeno_lice;
    private String drukao;
    private String obezbedjuje_materijal;
    private String obezbedjuje_supstancu;
    private String prisustvo_supstanci;
    private String kazna;

    @Override
    public String toString() {
        return "CaseDescription{" +
                "id=" + id +
                ", sud='" + sud + '\'' +
                ", poslovniBroj='" + poslovniBroj + '\'' +
                ", sudija='" + sudija + '\'' +
                ", okrivljeni='" + okrivljeni + '\'' +
                ", krivicnoDelo='" + krivicnoDelo + '\'' +
                ", supstanca='" + supstanca + '\'' +
                ", zapisnicar='" + zapisnicar + '\'' +
                ", prodaje='" + prodaje + '\'' +
                ", proizvodi='" + proizvodi + '\'' +
                ", poseduje='" + poseduje + '\'' +
                ", posreduje='" + posreduje + '\'' +
                ", usao_u_zemlju='" + usao_u_zemlju + '\'' +
                ", organizuje_mrezu='" + organizuje_mrezu + '\'' +
                ", prodaje_ugrozenim='" + prodaje_ugrozenim + '\'' +
                ", is_sluzbeno_lice='" + is_sluzbeno_lice + '\'' +
                ", drukao='" + drukao + '\'' +
                ", obezbedjuje_materijal='" + obezbedjuje_materijal + '\'' +
                ", obezbedjuje_supstancu='" + obezbedjuje_supstancu + '\'' +
                ", prisustvo_supstanci='" + prisustvo_supstanci + '\'' +
                ", kazna=" + kazna +
                '}';
    }

    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", Integer.class);
    }
}

