package bffui.composeruntime.poc.data

import bffui.composeruntime.poc.ProtobufData

inline var ProtobufData.id: String
  get() = get("id") as? String ?: error("'id' not found")
  set(value) {
    put("id", value)
  }


