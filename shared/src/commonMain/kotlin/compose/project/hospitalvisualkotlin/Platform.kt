package compose.project.hospitalvisualkotlin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform