import java.time.LocalDate


open class Persona(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: LocalDate,
    val direccion: String,
    val ciudadProcedencia: String
)


class Paciente(
    dni: String,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
    val numeroHistoriaClinica: String,
    val sexo: String,
    val grupoSanguineo: String,
    val alergias: List<String>
) : Persona(dni, nombre, apellido, fechaNacimiento, direccion, ciudadProcedencia)


open class Empleado(
    dni: String,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
    val codigoEmpleado: String,
    val horasExtras: Int,
    val fechaIngreso: LocalDate,
    val area: String,
    val cargo: String
) : Persona(dni, nombre, apellido, fechaNacimiento, direccion, ciudadProcedencia)


class EmpleadoPlanilla(
    dni: String,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
    codigoEmpleado: String,
    horasExtras: Int,
    fechaIngreso: LocalDate,
    area: String,
    cargo: String,
    val salarioMensual: Double,
    val porcentajeExtraPorHora: Double
) : Empleado(dni, nombre, apellido, fechaNacimiento, direccion, ciudadProcedencia, codigoEmpleado, horasExtras, fechaIngreso, area, cargo)


class EmpleadoEventual(
    dni: String,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
    codigoEmpleado: String,
    horasExtras: Int,
    fechaIngreso: LocalDate,
    area: String,
    cargo: String,
    val honorariosPorHora: Double,
    val totalHorasTrabajadas: Int,
    val fechaTerminoContrato: LocalDate
) : Empleado(dni, nombre, apellido, fechaNacimiento, direccion, ciudadProcedencia, codigoEmpleado, horasExtras, fechaIngreso, area, cargo)


class Medico(
    dni: String,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
    codigoEmpleado: String,
    horasExtras: Int,
    fechaIngreso: LocalDate,
    area: String,
    cargo: String,
    salarioMensual: Double,
    porcentajeExtraPorHora: Double,
    val especialidad: String,
    val servicio: String,
    val numeroConsultorio: Int

) : EmpleadoPlanilla(dni, nombre, apellido, fechaNacimiento, direccion, ciudadProcedencia, codigoEmpleado, horasExtras, fechaIngreso, area, cargo, salarioMensual, porcentajeExtraPorHora)


data class CitaMedica(
    val paciente: Paciente,
    val medico: Medico,
    val fecha: LocalDate,
    val hora: String,
    val servicio: String
)


class Hospital {
    private val empleados = mutableListOf<Empleado>()
    private val pacientes = mutableListOf<Paciente>()
    private val medicos = mutableListOf<Medico>()
    private val citasMedicas = mutableListOf<CitaMedica>()

    // Registrar empleados
    fun registrarEmpleado(empleado: Empleado) {
        empleados.add(empleado)
        if (empleado is Medico) {
            medicos.add(empleado)
        }
    }


    fun registrarPaciente(paciente: Paciente) {
        pacientes.add(paciente)
    }


    fun registrarCitaMedica(cita: CitaMedica) {
        citasMedicas.add(cita)
    }


    fun listarMedicosPorEspecialidad(especialidad: String): List<Medico> {
        return medicos.filter { it.especialidad.equals(especialidad, ignoreCase = true) }
    }


    fun listarPacientesPorMedico(codigoMedico: String): List<Paciente> {
        return citasMedicas.filter { it.medico.codigoEmpleado == codigoMedico }
            .map { it.paciente }
            .distinct()
    }
}


fun main() {
    val hospital = Hospital()


    val paciente1 = Paciente("12345678", "Juan", "Perez", LocalDate.of(1990, 5, 20), "Calle Falsa 123", "Lima", "HC123", "M", "O+", listOf("Penicilina"))
    val paciente2 = Paciente("87654321", "Maria", "Lopez", LocalDate.of(1985, 8, 10), "Avenida Siempre Viva 456", "Arequipa", "HC456", "F", "A-", listOf("Ninguna"))


    val medico1 = Medico("11223344", "Carlos", "Gomez", LocalDate.of(1975, 1, 15), "Av. Las Flores 789", "Lima", "EMP001", 5, LocalDate.of(2000, 1, 1), "Cirugía", "Médico", 5000.0, 10.0, "Cirujano", "Cirugía", 101)
    val medico2 = Medico("55667788", "Ana", "Martinez", LocalDate.of(1980, 3, 30), "Jr. Los Olivos 321", "Trujillo", "EMP002", 3, LocalDate.of(2005, 5, 10), "Oftalmología", "Médico", 4500.0, 8.0, "Oftalmólogo", "Oftalmología", 102)


    hospital.registrarPaciente(paciente1)
    hospital.registrarPaciente(paciente2)


    hospital.registrarEmpleado(medico1)
    hospital.registrarEmpleado(medico2)


    val cita1 = CitaMedica(paciente1, medico1, LocalDate.of(2024, 9, 1), "10:00 AM", "Cirugía")
    val cita2 = CitaMedica(paciente2, medico2, LocalDate.of(2024, 9, 2), "11:00 AM", "Oftalmología")

    hospital.registrarCitaMedica(cita1)
    hospital.registrarCitaMedica(cita2)


    println("Médicos en Cirugía:")
    hospital.listarMedicosPorEspecialidad("Cirugía").forEach { println(it.nombre + " " + it.apellido) }

    
    println("Pacientes atendidos por el médico EMP001 (Carlos Gomez):")
    hospital.listarPacientesPorMedico("EMP001").forEach { println(it.nombre + " " + it.apellido) }
}
