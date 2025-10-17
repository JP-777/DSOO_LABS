
public class Doctor{
    private int código;
    private String nombre;
    private String especialidad;
    private String horarioAtencion;
    public Doctor(int codigo, String nombre, String especialidad, String horarioAtencion){
        this.codigo = codigo;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.horarioAtencion = horarioAtencion;
    }
    public int getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public String getEspecialidad(){
        return especialidad;
    }
    public String getHorarioAtencion(){
        return horarioAtencion;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }
    public void setHorarioAtencion(String horarioAtencion){
        this.horarioAtencion = horarioAtencion;
    }

}