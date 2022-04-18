package johan.santos.reservesisha.ui.access.models

data class DataBooking(
    var nom_business    : String,
    var nom_reserva     : String,
    var num_personas    : Int,
    var fecha           : String,
    var hora            : String,
    var tipo_reserva    : String,
    var direccion       : String,
    var id_user         : String,
    var id_empresa      : String,
    var id_booking      : String,
    var tarifa          : Int,
    var confirmada      : Boolean
)
