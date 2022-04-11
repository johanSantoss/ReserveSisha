package johan.santos.reservesisha.ui.access.models

import java.util.*

data class User_Current  (
    override var id_usuari  : String,
    override var nom_usuari : String,
    override var email      : String,
    override var rol        : String,
    var nom         : String,
    var cognoms     : String,
    var edat        : String,
    var sexe        : String,
    var data_naixement : String,
    var ciutat      : String,
    var identificador_personal : String

) : User(){

}
