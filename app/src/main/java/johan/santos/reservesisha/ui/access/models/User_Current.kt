package johan.santos.reservesisha.ui.access.models

import java.util.*

data class User_Current  (
    override var id_usuari  : String,
    override var nom_usuari : String,
    override var email      : String,
    override var password   : String,
    override var rol        : String,
    var id_personal : String,
    var nom         : String,
    var cognoms     : String,
    var edat        : Int,
    var sexe        : String,
    var ciutat      : String,
    var data_naixement : Date

) : User()
