package cn.libnavcompiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import cn.libnavannotation.ActivityDestination;
import cn.libnavannotation.FragmentDestination;


/**
 * @author lancet
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"cn.libnavannotation.FragmentDestination","cn.libnavannotation.ActivityDestination"})
public class NavProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private static final String OUTPUT_FILE_NAME = "destination.json";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> fragmentElements = roundEnvironment.getElementsAnnotatedWith(FragmentDestination.class);
        Set<? extends Element> activityElements = roundEnvironment.getElementsAnnotatedWith(ActivityDestination.class);
        if (!activityElements.isEmpty() || !fragmentElements.isEmpty()){
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(fragmentElements,FragmentDestination.class,destMap);
            handleDestination(activityElements,ActivityDestination.class,destMap);

            FileOutputStream fileOutputStream = null;
            OutputStreamWriter outputStreamWriter = null;
//            app/src/main/assets/
            try {
                FileObject resource= filer.createResource(StandardLocation.CLASS_OUTPUT,"", OUTPUT_FILE_NAME);
                String resourcePath = resource.toUri().getPath();
                String appPath = resourcePath.substring(0,resourcePath.indexOf("app")+4);
                String assetsPath = appPath + "src/main/assets/";

                File file = new File(assetsPath);
                if (!file.exists()){
                    file.mkdirs();
                }
                File outputFile = new File(file,OUTPUT_FILE_NAME);
                if (outputFile.exists()){
                    outputFile.delete();
                }
                outputFile.createNewFile();

                String content = JSON.toJSONString(destMap);
                fileOutputStream = new FileOutputStream(outputFile);
                outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");

                outputStreamWriter.write(content);
                outputStreamWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if (outputStreamWriter!=null){
                    try {
                        outputStreamWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutputStream!=null){
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    private void handleDestination(Set<? extends Element> fragmentElements, Class<? extends Annotation> annotationClass, HashMap<String, JSONObject> destMap) {
        for(Element element : fragmentElements){
            TypeElement typeElement = (TypeElement) element;
            String pageUrl = null;
            String className = typeElement.getQualifiedName().toString();
            int destinationId = Math.abs(className.hashCode());
            boolean needLogin = false;
            boolean asStarter = false;
            boolean isFragment = false;

            Annotation annotation = typeElement.getAnnotation(annotationClass);
            if (annotation instanceof FragmentDestination){
                FragmentDestination dest = (FragmentDestination) annotation;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
            }else if (annotation instanceof ActivityDestination){
                ActivityDestination dest = (ActivityDestination) annotation;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
            }

            if(destMap.containsKey(pageUrl)){
                messager.printMessage(Diagnostic.Kind.ERROR,"不同页面禁止使用同一个PageUrl"+className);
            }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("destinationId",destinationId);
                jsonObject.put("pageUrl",pageUrl);
                jsonObject.put("needLogin",needLogin);
                jsonObject.put("asStarter",asStarter);
                jsonObject.put("className",className);
                jsonObject.put("isFragment",isFragment);
            }


        }
    }



}
