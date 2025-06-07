package bff.ui

public class BffUiCodegenException(explanation: String? = null) : RuntimeException() {
  override val message: String =
    "BFF UI Schema 코드젠 로직이 잘못되었거나 오래된 Schema를 사용하고 있습니다.\n" +
      "Protobuf Schema가 업데이트되었다면 코드젠을 다시 실행해 주세요." +
      explanation?.let { "\n\n$it" }.orEmpty()
}