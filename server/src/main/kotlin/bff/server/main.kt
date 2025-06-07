package bff.server

import bff.ui.Actions
import bff.ui.Attributes
import bff.ui.protobufScreen
import bff.ui.schema.ProtoSizeArea
import bff.ui.schema.layoutPadding
import bff.ui.schema.size
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.wire.WireJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import protobuf.source.action.Action.TriggerCondition
import protobuf.source.action.Action.Type
import protobuf.source.attributes.Attributes.SizeArea
import protobuf.source.component.CellColor
import protobuf.source.component.CellDividerComponent
import protobuf.source.component.CellTextComponent
import protobuf.source.component.CellTextStyle
import protobuf.source.response.Response
import protobuf.source.section.Section

fun main() {
  val bffUiResponse = runBlocking {
    protobufScreen {
      RootScreen(
        Attributes
          .size(SizeArea.SIZE_AREA_WIDTH, 10f)
          .size(SizeArea.SIZE_AREA_HEIGHT, 10f),
        Actions
          .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://example.com")
          .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
      ) {
        BodySection(
          Attributes
            .size(SizeArea.SIZE_AREA_WIDTH, 10f)
            .size(SizeArea.SIZE_AREA_HEIGHT, 10f),
          Actions
            .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://example.com")
            .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
          stackDirection = Section.StackDirection.STACK_DIRECTION_VERTICAL,
        ) {
          SearchHospitalAWidget(
            Attributes
              .size(SizeArea.SIZE_AREA_WIDTH, 123f)
              .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
            Actions
              .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
              .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
            isSticky = true,
            aEventItem = {
              AEventChildWidget(
                attributes = Attributes.layoutPadding(top = 10f, leading = 31f),
                actions = Actions.action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                divider = {
                  CellDividerComponent(
                    Attributes
                      .size(SizeArea.SIZE_AREA_WIDTH, 2f)
                      .size(SizeArea.SIZE_AREA_HEIGHT, 4f),
                    Actions
                      .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://333.com")
                      .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                    style = CellDividerComponent.Style.STYLE_SECTION,
                    text = {
                      CellTextComponent(
                        Attributes.layoutPadding(top = 10f, leading = 31f),
                        Actions
                          .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://34.com")
                          .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                        style = CellTextStyle.CELL_TEXT_STYLE_BOLD,
                        color = CellColor.CELL_COLOR_BLACK,
                        text = "my text",
                      )
                    },
                  )
                },
                bEventItems = {
                  repeat(5) { count ->
                    BEventChildWidget(
                      Attributes.size(ProtoSizeArea.SIZE_AREA_WIDTH, count.toFloat()),
                      Actions.action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://$count.com"),
                      divider = {
                        CellDividerComponent(
                          Attributes
                            .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                            .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                          Actions
                            .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                            .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                          style = CellDividerComponent.Style.STYLE_SECTION,
                          text = {
                            CellTextComponent(
                              Attributes
                                .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                                .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                              Actions
                                .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                                .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                              style = CellTextStyle.CELL_TEXT_STYLE_BOLD,
                              color = CellColor.CELL_COLOR_WHITE,
                              text = "My #$count B item",
                            )
                          },
                        )
                      },
                    )
                  }
                },
              )
            },
            hospitalName = {
              CellTextComponent(
                Attributes
                  .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                  .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                Actions
                  .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                  .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                style = CellTextStyle.CELL_TEXT_STYLE_BOLD,
                color = CellColor.CELL_COLOR_WHITE,
                text = "HospitalName",
              )
            },
            infoText = {
              CellTextComponent(
                Attributes
                  .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                  .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                Actions
                  .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                  .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                style = CellTextStyle.CELL_TEXT_STYLE_BOLD,
                color = CellColor.CELL_COLOR_WHITE,
                text = "InfoText",
              )
            },
            divider = {
              CellDividerComponent(
                Attributes
                  .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                  .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                Actions
                  .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                  .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                style = CellDividerComponent.Style.STYLE_SECTION,
                text = {
                  CellTextComponent(
                    Attributes
                      .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                      .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                    Actions
                      .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                      .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                    style = CellTextStyle.CELL_TEXT_STYLE_BOLD,
                    color = CellColor.CELL_COLOR_WHITE,
                    text = "My divider",
                  )
                },
              )
            },
          )
          CellDividerWidget(
            Attributes
              .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
              .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
            Actions
              .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
              .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
            isSticky = false,
            color = CellColor.CELL_COLOR_BLACK,
            divider = {
              CellDividerComponent(
                Attributes
                  .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                  .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                Actions
                  .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                  .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                style = CellDividerComponent.Style.STYLE_SECTION,
                text = {
                  CellTextComponent(
                    Attributes
                      .size(SizeArea.SIZE_AREA_WIDTH, 12310f)
                      .size(SizeArea.SIZE_AREA_HEIGHT, 10242f),
                    Actions
                      .action(Type.TYPE_URI_SCHEME, TriggerCondition.TRIGGER_CONDITION_IMMEDIATE, "https://222.com")
                      .action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                    style = CellTextStyle.CELL_TEXT_STYLE_BOLD,
                    color = CellColor.CELL_COLOR_WHITE,
                    text = "asd",
                  )
                },
              )
            }
          )
        }
      }
    }
  }

  val moshi =
    Moshi.Builder()
      .add(WireJsonAdapterFactory(writeIdentityValues = true))
      .build()
  val responseAdaptor = moshi.adapter<Response>().indent("  ")

  println(responseAdaptor.toJson(bffUiResponse))
}
