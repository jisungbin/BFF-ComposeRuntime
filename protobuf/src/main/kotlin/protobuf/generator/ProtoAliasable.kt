package protobuf.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeName
import protobuf.source.attributes.Attributes

internal enum class AliasMode {
  Import, TypeAlias,
}

internal abstract class ProtoAliasable(
  private val myPackageName: String,
  private val mode: AliasMode = AliasMode.Import,
) {
  private val aliasedImports = mutableMapOf<ClassName, String>()
  private var usesProtoAttributes = false

  fun ClassName.protoAliased(): TypeName {
    if (!packageName.startsWith("protobuf.source")) return this

    val aliased = run {
      if (mode == AliasMode.TypeAlias && canonicalName.startsWith(protoAttributesFqn)) {
        usesProtoAttributes = true
        ClassName.bestGuess(canonicalName.replace(protoAttributesFqn, "$myPackageName.ProtoAttributes"))
      } else {
        this
      }
    }

    val newSimpleName = "Proto${simpleName}"
    aliasedImports[aliased] = newSimpleName

    return when (mode) {
      AliasMode.TypeAlias -> ClassName(myPackageName, newSimpleName)
      AliasMode.Import -> aliased
    }
  }

  fun FileSpec.Builder.applyAliased(): FileSpec.Builder = apply {
    if (usesProtoAttributes) applyProtoAttributesAlias()
    when (mode) {
      AliasMode.TypeAlias -> {
        aliasedImports.forEach { (cn, alias) ->
          addTypeAlias(TypeAliasSpec.builder(alias, cn).build())
        }
      }
      AliasMode.Import -> {
        aliasedImports.forEach { (cn, alias) -> addAliasedImport(cn, alias) }
      }
    }
  }

  internal companion object {
    private val protoAttributesFqn = Attributes::class.qualifiedName!!

    internal fun FileSpec.Builder.applyProtoAttributesAlias(): FileSpec.Builder =
      addAliasedImport(Attributes::class, "ProtoAttributes")
  }
}
