package johan.santos.reservesisha.ui.access.models

abstract class User {
    abstract var id_usuari : String
    abstract var nom_usuari : String
    abstract var email      : String
    abstract var password   : String
    abstract var rol        : String
}
