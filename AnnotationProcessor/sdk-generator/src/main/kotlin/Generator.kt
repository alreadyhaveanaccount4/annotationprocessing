import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import me.usrName.annotationprocessor.Entity
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic


@AutoService(Processor::class)
class Generator : AbstractProcessor() {


    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Entity::class.java.name)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        val elementsWithConstant = roundEnv.getElementsAnnotatedWith(Entity::class.java)
         if (elementsWithConstant.isEmpty()) {
             return true
         }
         val fileName = "MyGeneratedClass"
         val packageName = "me.usrName.sdk.generated"
         val classBuilder = TypeSpec.classBuilder(fileName)

         for (element in elementsWithConstant) {
             val ann = element.getAnnotation(Entity::class.java)

             val variableBuilder =
                 PropertySpec.varBuilder(
                     name = element.simpleName.toString(),
                     type = element.asType().asTypeName().asNullable()
                 ).initializer("null")
             //arrayOf(element.asType().asTypeName().asNullable())


             //val variable = PropertySpec.varBuilder(enclosed.simpleName.toString(), enclosed.asType().asTypeName().asNullable(), KModifier.PRIVATE)
             val test = PropertySpec.builder(
                 name = ann.propName,
                 type = ClassName("", element.simpleName.toString()),
                 KModifier.PUBLIC
             ).mutable(true)


             /*val funcBuilder = FunSpec.builder("__get__").addModifiers(KModifier.PUBLIC)
                 .addParameter("testParam", typeOf<Int>().asTypeName())*/

             /*val propBuilder = PropertySpec.builder(
                 name = propName,
                 type = ClassName("kotlin", "String"),
                 KModifier.CONST,KModifier.FINAL
                 ).mutable(false).initializer("\"2\"")*/

             classBuilder
                 .addProperty(variableBuilder.build())

         }
         val file = FileSpec.builder(packageName, fileName)
             .addType(classBuilder.build())
             .build()
         val generatedDirectory = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
         file.writeTo(File(generatedDirectory, "$fileName.kt"))
         return true
        println("##############")

    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}


//