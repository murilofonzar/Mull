package com.example.mull.Dao;

public class Coletas {

 String DataColeta;
 String HorarioColeta;
 String HorarioColetaFim;
 String FotoColeta;
 String TipoColeta;

    public Coletas() {
    }

    public Coletas(String Dia, String HorarioInicial, String HorarioFinal) {
        this.DataColeta = Dia;
        this.HorarioColeta = HorarioInicial;
        this.HorarioColetaFim = HorarioFinal;
    }


    public String getDataColeta() {
        return DataColeta;
    }

    public void setDataColeta(String dataColeta) {
        DataColeta = dataColeta;
    }

    public String getHorarioColeta() {
        return HorarioColeta;
    }

    public void setHorarioColeta(String horarioColeta) {
        HorarioColeta = horarioColeta;
    }

    public String getHorarioColetaFim() {
        return HorarioColetaFim;
    }

    public void setHorarioColetaFim(String horarioColetaFim) {
        HorarioColetaFim = horarioColetaFim;
    }

    public String getFotoColeta() {
        return FotoColeta;
    }

    public void setFotoColeta(String fotoColeta) {
        FotoColeta = fotoColeta;
    }

    public String getTipoColeta() {
        return TipoColeta;
    }

    public void setTipoColeta(String tipoColeta) {
        TipoColeta = tipoColeta;
    }
}
