package model;

public class Cita {
    private int codigo;
    private Doctor doctor;
    private Paciente paciente;
    private String fecha;
    private String hora;
    private String motivo;

    public Cita(int codigo, Doctor doctor, Paciente paciente, String fecha, String hora, String motivo) {
        this.codigo = codigo;
        this.doctor = doctor;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }

    public int getCodigo() { return codigo; }
    public Doctor getDoctor() { return doctor; }
    public Paciente getPaciente() { return paciente; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getMotivo() { return motivo; }

    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    @Override
    public String toString() {
        return "Cita{" +
                "codigo=" + codigo +
                ", doctor=" + doctor.getNombre() +
                ", paciente=" + paciente.getNombre() +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}