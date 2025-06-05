package bffui.composeruntime.poc

import bff.ui.action.Actions
import bff.ui.attribute.Attributes
import bff.ui.protobufUi
import bff.ui.schema.padding
import bff.ui.schema.size
import java.awt.SystemColor.text
import kotlinx.coroutines.runBlocking
import protobuf.source.action.Action.TriggerCondition
import protobuf.source.action.Action.Type
import protobuf.source.attributes.Attributes.SizeArea
import protobuf.source.component.CellColor
import protobuf.source.component.CellDividerComponent
import protobuf.source.component.CellTextStyle
import protobuf.source.section.Section

fun main() {
  val bffUiJson = runBlocking {
    protobufUi {
      RootScreen {
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
            aEventItems = {
              AEventChildWidget(
                attributes = Attributes.padding(top = 10f, leading = 31f),
                actions = Actions.action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                divider = {
                  CellDividerComponent(style = CellDividerComponent.Style.STYLE_SECTION) {
                    CellTextComponent(
                      attributes = Attributes.padding(trailing = 1235f),
                      actions = Actions.action(Type.TYPE_LOGGING, TriggerCondition.TRIGGER_CONDITION_TAP),
                      style = CellTextStyle.CELL_TEXT_STYLE_BOLD,
                      color = CellColor.CELL_COLOR_BLACK,
                      text = "",
                    )
                  }
                },
                bEventItems = {},
              )
            },
            hospitalName = {},
            infoText = {},
            divider = {},
          )
        }
      }
    }
  }
  println(bffUiJson)
}

