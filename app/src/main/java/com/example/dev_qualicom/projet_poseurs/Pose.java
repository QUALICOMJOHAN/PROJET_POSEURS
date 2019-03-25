package com.example.dev_qualicom.projet_poseurs;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Pose {

    public Timestamp x_date_pose;
    ArrayList<String> techniciens = new ArrayList<String>();
    String name;
    String equipe;
    String city;
    String mobile;
    String phone;
    String street;
    String zip;
    Long id;
    ArrayList<String> x_rapport_autoconsommation = new ArrayList<String>();
    ArrayList<String> x_rapport_ballon_thermodynamique = new ArrayList<String>();
    ArrayList<String> x_rapport_batterie = new ArrayList<String>();
    ArrayList<String> x_rapport_booster = new ArrayList<String>();
    ArrayList<String> x_rapport_complement_pose = new ArrayList<String>();
    ArrayList<String> x_rapport_pac_air_air = new ArrayList<String>();
    ArrayList<String> x_rapport_pac_air_eau = new ArrayList<String>();
    ArrayList<String> x_rapport_remise_niveau = new ArrayList<String>();

    Boolean x_autoconsommation;
    Boolean x_ballon_thermodynamique;
    Boolean x_batterie;
    Boolean x_booster;
    Boolean x_complement_pose;
    Boolean x_pac_air_air;
    Boolean x_pac_air_eau;
    Boolean x_remise_niveau;

    String vendeur;
    Timestamp x_date_vente;
    Timestamp start_pose;
    String societe;
    boolean x_dossier_financement;

    public Boolean getX_autoconsommation() {
        return x_autoconsommation;
    }

    public void setX_autoconsommation(Boolean x_autoconsommation) {
        this.x_autoconsommation = x_autoconsommation;
    }

    public Boolean getX_ballon_thermodynamique() {
        return x_ballon_thermodynamique;
    }

    public void setX_ballon_thermodynamique(Boolean x_ballon_thermodynamique) {
        this.x_ballon_thermodynamique = x_ballon_thermodynamique;
    }

    public Boolean getX_batterie() {
        return x_batterie;
    }

    public void setX_batterie(Boolean x_batterie) {
        this.x_batterie = x_batterie;
    }

    public Boolean getX_booster() {
        return x_booster;
    }

    public void setX_booster(Boolean x_booster) {
        this.x_booster = x_booster;
    }

    public Boolean getX_complement_pose() {
        return x_complement_pose;
    }

    public void setX_complement_pose(Boolean x_complement_pose) {
        this.x_complement_pose = x_complement_pose;
    }

    public Boolean getX_pac_air_air() {
        return x_pac_air_air;
    }

    public void setX_pac_air_air(Boolean x_pac_air_air) {
        this.x_pac_air_air = x_pac_air_air;
    }

    public Boolean getX_pac_air_eau() {
        return x_pac_air_eau;
    }

    public void setX_pac_air_eau(Boolean x_pac_air_eau) {
        this.x_pac_air_eau = x_pac_air_eau;
    }

    public Boolean getX_remise_niveau() {
        return x_remise_niveau;
    }

    public void setX_remise_niveau(Boolean x_remise_niveau) {
        this.x_remise_niveau = x_remise_niveau;
    }

    public boolean isX_dossier_financement() {
        return x_dossier_financement;
    }

    public void setX_dossier_financement(boolean x_dossier_financement) {
        this.x_dossier_financement = x_dossier_financement;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public Timestamp getStart_pose() {
        return start_pose;
    }

    public void setStart_pose(Timestamp start_pose) {
        this.start_pose = start_pose;
    }

    public Timestamp getX_date_vente() {
        return x_date_vente;
    }

    public void setX_date_vente(Timestamp x_date_vente) {
        this.x_date_vente = x_date_vente;
    }

    public String getVendeur() {
        return vendeur;
    }

    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }

    public ArrayList<String> getX_rapport_autoconsommation() {
        return x_rapport_autoconsommation;
    }

    public void setX_rapport_autoconsommation(ArrayList<String> x_rapport_autoconsommation) {
        this.x_rapport_autoconsommation = x_rapport_autoconsommation;
    }

    public ArrayList<String> getX_rapport_ballon_thermodynamique() {
        return x_rapport_ballon_thermodynamique;
    }

    public void setX_rapport_ballon_thermodynamique(ArrayList<String> x_rapport_ballon_thermodynamique) {
        this.x_rapport_ballon_thermodynamique = x_rapport_ballon_thermodynamique;
    }

    public ArrayList<String> getX_rapport_batterie() {
        return x_rapport_batterie;
    }

    public void setX_rapport_batterie(ArrayList<String> x_rapport_batterie) {
        this.x_rapport_batterie = x_rapport_batterie;
    }

    public ArrayList<String> getX_rapport_booster() {
        return x_rapport_booster;
    }

    public void setX_rapport_booster(ArrayList<String> x_rapport_booster) {
        this.x_rapport_booster = x_rapport_booster;
    }

    public ArrayList<String> getX_rapport_complement_pose() {
        return x_rapport_complement_pose;
    }

    public void setX_rapport_complement_pose(ArrayList<String> x_rapport_complement_pose) {
        this.x_rapport_complement_pose = x_rapport_complement_pose;
    }

    public ArrayList<String> getX_rapport_pac_air_air() {
        return x_rapport_pac_air_air;
    }

    public void setX_rapport_pac_air_air(ArrayList<String> x_rapport_pac_air_air) {
        this.x_rapport_pac_air_air = x_rapport_pac_air_air;
    }

    public ArrayList<String> getX_rapport_pac_air_eau() {
        return x_rapport_pac_air_eau;
    }

    public void setX_rapport_pac_air_eau(ArrayList<String> x_rapport_pac_air_eau) {
        this.x_rapport_pac_air_eau = x_rapport_pac_air_eau;
    }

    public ArrayList<String> getX_rapport_remise_niveau() {
        return x_rapport_remise_niveau;
    }

    public void setX_rapport_remise_niveau(ArrayList<String> x_rapport_remise_niveau) {
        this.x_rapport_remise_niveau = x_rapport_remise_niveau;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public Timestamp getX_date_pose() {
        return x_date_pose;
    }

    public void setX_date_pose(Timestamp x_date_pose) {
        this.x_date_pose = x_date_pose;
    }

    public ArrayList<String> getTechniciens() {
        return techniciens;
    }

    public void setTechniciens(ArrayList<String> techniciens) {
        this.techniciens = techniciens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getListe(){

        ArrayList<String> array = new ArrayList<String>();

        if(x_autoconsommation) {
            array.add("\n"+"Autoconsommation"+"\n");
        }
        for (String s : x_rapport_autoconsommation) {
            array.add(s);
        }
        if(x_ballon_thermodynamique) {
            array.add("\n"+"Ballon thermodynamique"+"\n");
        }
        for (String s : x_rapport_ballon_thermodynamique) {
            array.add(s);
        }
        if(x_batterie) {
            array.add("\n"+"Batterie"+"\n");
        }
        for (String s : x_rapport_batterie) {
            array.add(s);
        }
        if(x_booster) {
            array.add("\n"+"Booster"+"\n");
        }
        for (String s : x_rapport_booster) {
            array.add(s);
        }
        if(x_complement_pose) {
            array.add("\n"+"Complement pose"+"\n");
        }
        for (String s : x_rapport_complement_pose) {
            array.add(s);
        }
        if(x_pac_air_air) {
            array.add("\n"+"PAC AIR/AIR"+"\n");
        }
        for (String s : x_rapport_pac_air_air) {
            array.add(s);
        }
        if(x_pac_air_eau) {
            array.add("\n"+"PAC AIR/EAU"+"\n");
        }
        for (String s : x_rapport_pac_air_eau) {
            array.add(s);
        }
        if(x_remise_niveau) {
            array.add("\n"+"Remise a niveau"+"\n");
        }
        for (String s : x_rapport_remise_niveau) {
            array.add(s);
        }

        return array;
    }
}
