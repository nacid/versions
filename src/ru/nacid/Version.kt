package ru.nacid

data class Version(
    val major: Int,
    val middle: Int,
    val minor: Int
) {
    constructor(major: Int, middle: Int?, minor: Int?) : this(major, middle ?: 0, minor ?: 0)

    companion object {
        private const val pattern = "^(?<major>\\d+)(\\.(?<middle>\\d+))?(\\.(?<minor>\\d+))?$"

        fun from(string: String?) = string?.let {
            pattern.toRegex()
                .matchEntire(string)
                ?.let { matchResult ->
                    from(
                        matchResult.groups["major"]?.value,
                        matchResult.groups["middle"]?.value,
                        matchResult.groups["minor"]?.value
                    )
                }
        }


        fun from(major: String?, middle: String?, minor: String?) =
            from(major?.toIntOrNull(), middle?.toIntOrNull(), minor?.toIntOrNull())

        fun from(major: Int?, middle: Int?, minor: Int?) =
            major?.let { Version(major, middle, minor) }
    }

    override fun toString(): String {
        return "$major.$middle.$minor"
    }
}