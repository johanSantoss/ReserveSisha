package johan.santos.reservesisha.ui.access.models

data class DataBusiness(
    var cif         : String = "",
    var nom_business: String = "",
    var direccio    : String = "",
    var telefono    : String = "",
    var descripcio  : String = "",
    var horaOpen    : String = "",
    var horaClose   : String = "",
    var logo        : String = ""
)
