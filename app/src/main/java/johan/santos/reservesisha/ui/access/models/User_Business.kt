package johan.santos.reservesisha.ui.access.models

data class User_Business(
    override var id_usuari  : String,
    override var nom_usuari : String,
    override var email      : String,
    override var rol        : String,
    var nom_business    : String,
    var direccio        : String,
    var telefon         : String,
    var descripcio      : String,
    var cif             : String
) : User()
