package au.yazilimstaj.perfectplace

data class Gunler (
    val id: Int,
    val name: String,
    val person_limit: Int = 10,
    var person_contained_number: Int
)



