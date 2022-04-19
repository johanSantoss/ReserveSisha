package johan.santos.reservesisha.ui.access.models

import java.util.*

data class User_Admin(
    override var id_usuari  : String,
    override var nom_usuari : String,
    override var email      : String,
    override var rol        : String,
    var data_update         : String
) : User()
