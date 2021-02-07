package com.isee.community.cache;

import com.isee.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagDTOS.add(tool);
        return tagDTOS;
    }


    /**
     * 验证标签是否合法
     * @param tags
     * @return
     */
    public static String filterInvalid(String tags) {
        String[] selectedTags = tags.split(",");
        List<TagDTO> validTagDTOs = get();
        //二重循环提取出所有合法标签
        List<String> tagList = validTagDTOs.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //判断所选标签是否合法,拦截所有非法标签
        String invalid = Arrays.stream(selectedTags).filter(t->(StringUtils.isBlank(t)||!tagList.contains(t))).collect(Collectors.joining(","));
        return invalid;
    }


    public static void main(String[] args) {
        List<TagDTO> validTagDTOs = get();
        List<String> tagList = validTagDTOs.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        for(TagDTO tagDTO:validTagDTOs){
            for(String s:tagDTO.getTags()){
                list.add(s);
            }
        }
        System.out.println(list);
    }
}
