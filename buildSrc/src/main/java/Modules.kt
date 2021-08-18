interface ProjectModule {

    val path: String
}

enum class Application(override val path: String): ProjectModule {
    App(":app");
}

enum class Features(override val path: String): ProjectModule {
    Auth(":Features:Auth"),
    Chats(":Features:Chats"),
    Contacts(":Features:Contacts"),
    Conversations(":Features:Conversations"),
    CreatingAccount(":Features:CreatingAccount"),
    OnBoarding(":Features:OnBoarding"),
    Profiles(":Features:Profiles"),
    Settings(":Features:Settings");
}

enum class Core(override val path: String): ProjectModule {
    Base(":Core:Base"),
    Data(":Core:Data"),
    Network(":Core:Network");
}

enum class ChatKit(override val path: String): ProjectModule {
    ChatCore(":ChatKit:ChatCore"),
    ChatUI(":ChatKit:ChatUI");
}

enum class Additional(override val path: String): ProjectModule {
    Security(":Security"),
    UIKit(":UIKit");
}
