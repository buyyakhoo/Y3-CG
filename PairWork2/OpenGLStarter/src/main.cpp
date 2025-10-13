#undef GLFW_DLL
#include <iostream>
#include <stdio.h>
#include <string>
#include <string.h>

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include <vector>
#include <cmath>

#include "Libs/Shader.h"
#include "Libs/Window.h"
#include "Libs/Mesh.h"

#include "Libs/stb_image.h"

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>


const GLint WIDTH = 800, HEIGHT = 600;

Window mainWindow;
std::vector<Mesh*> meshList;
std::vector<Shader*> shaderList;

Mesh* light;
static const char* lightVShader = "Shaders/lightShader.vert";
static const char* lightFShader = "Shaders/lightShader.frag";

float yaw = -90.0f, pitch = 0.0f;

//Vertex Shader
static const char* vShader = "Shaders/shader.vert";

//Fragment Shader
static const char* fShader = "Shaders/shader.frag";

Mesh* bg;
static const char* bgVShader = "Shaders/bgShader.vert";
static const char* bgFShader = "Shaders/bgShader.frag";

Shader* depthShader;
static const char* depthVShader = "Shaders/depthShader.vert";
static const char* depthFShader = "Shaders/depthShader.frag";

glm::vec3 lightColour = glm::vec3(1.0f, 1.0f, 1.0f);
glm::vec3 lightPos = glm::vec3(-10.0f, 0.0f, 10.0f);

GLuint uniformModel = 0;
GLuint uniformView = 0;
GLuint uniformProjection = 0;

// texture
unsigned int texture1, texture2, bgTexture;  

void CreateTriangle()
{
    GLfloat vertices[] =
    {
        //x,    y,      z,      u,    v   (Texture coordinate)
        -1.0f, -1.0f, 0.0f,     0.0f, 0.0f, 
        0.0f, -1.0f, 1.0f,      0.5f, 0.0f,
        1.0f, -1.0f, 0.0f,      1.0f, 0.0f,
        0.0f, 1.0f, 0.0f,       0.5f, 1.0f
    };

    unsigned int indices[] = 
    {
        0, 3, 1,
        1, 3, 2,
        2, 3, 0,
        0, 1, 2
    };

    Mesh *obj1 = new Mesh();
    obj1->CreateMesh(vertices, indices, 20, 12);

    // add 10 objects
    for (int i=0; i<10; i++) {
        meshList.push_back(obj1);
    }

}

void CreateOBJ()
{
    Mesh *obj1 = new Mesh();
    bool loaded = obj1->CreateMeshFromOBJ("Models/cat_minecraft_2.obj");
    
    if (loaded)
    {
        for (int i=0; i<10; i++)
        {
            meshList.push_back(obj1);
        }
    }
    else 
    {
        std::cout << "OBJ failed to load" << std::endl;
    }

    light = new Mesh();
    loaded = light->CreateMeshFromOBJ("Models/cube.obj");
    
    if (!loaded)
    {
        std::cout << "OBJ failed to load" << std::endl;
        delete(light);
    }

    bg = new Mesh();
    loaded = bg->CreateMeshFromOBJ("Models/cube.obj");
    
    if (!loaded)
    {
        std::cout << "OBJ failed to load" << std::endl;
        delete(bg);
    }

}

void CreateShaders()
{
    Shader* shader1 = new Shader();
    shader1->CreateFromFiles(vShader, fShader);
    shaderList.push_back(shader1);

    Shader* shader2 = new Shader();
    shader2->CreateFromFiles(lightVShader, lightFShader);
    shaderList.push_back(shader2);

    Shader* shader3 = new Shader();
    shader3->CreateFromFiles(bgVShader, bgFShader);
    shaderList.push_back(shader3);

    depthShader = new Shader();
    depthShader->CreateFromFiles(depthVShader, depthFShader);
}

void checkMouse()
{
    double mouseX, mouseY;
    
    glfwGetCursorPos(mainWindow.getWindow(), &mouseX, &mouseY);

    static double lastX = mouseX;
    static double lastY = mouseY;

    float xOffset = mouseX - lastX;
    float yOffset = lastY - mouseY;
    
    lastX = mouseX;
    lastY = mouseY;

    float sensitivity = 0.1f;
    xOffset *= sensitivity;
    yOffset *= sensitivity;

    yaw += xOffset;
    pitch += yOffset;

    if (pitch > 89.0f)
        pitch = 89.0f;
    if (pitch < -89.0f)
        pitch = -89.0f;
}

void RenderScene(glm::mat4 view, glm::mat4 projection)
{
    glm::vec3 pyramidPositions[] = {
        glm::vec3(1.0f, -2.0f, 0.5f),
        glm::vec3(2.0f, 5.0f, -15.0f),
        glm::vec3(-1.5f, -2.2f, -2.5f),
        glm::vec3(-3.8f, -2.0f, -12.3f),
        glm::vec3( 2.4f, -0.4f, -3.5f),
        glm::vec3(-1.7f, 3.0f, -7.5f),
        glm::vec3( 1.3f, -2.0f, -2.5f),
        glm::vec3( 1.5f, 2.0f, -2.5f),
        glm::vec3( 1.5f, 0.2f, -1.5f),
        glm::vec3(-1.3f, 1.0f, -1.5f)
    };

    // Object
    for (int i = 0; i < 10; i++)
    {
        glm::mat4 model (1.0f);
        
        model = glm::translate(model, pyramidPositions[i]);
        model = glm::rotate(model, glm::radians(2.0f * i), glm::vec3(1.0f, 0.3f, 0.5f));
        model = glm::scale(model, glm::vec3(0.8f, 0.8f, 1.0f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture1);
        meshList[i]->RenderMesh();  
    }
}

int main()
{
    mainWindow = Window(WIDTH, HEIGHT, 3, 3);
    mainWindow.initialise();

    CreateOBJ();
    CreateShaders();

    float deltaTime = 0.0f;
    float lastTime = 0.0f;

    // === TEXTURE 1 ===
    glGenTextures(1, &texture1);
    glBindTexture(GL_TEXTURE_2D, texture1);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int width1, height1, nrChannels1;
    unsigned char *data1 = stbi_load("Textures/cat_minecraft_2.png", &width1, &height1, &nrChannels1, 0);

    if (!data1) {
        std::cout << "Failed to load texture1: cat_minecraft_2.png" << std::endl;
    } else {
        GLenum format = GL_RGB;
        if (nrChannels1 == 1)
            format = GL_RED;
        else if (nrChannels1 == 3)
            format = GL_RGB;
        else if (nrChannels1 == 4)
            format = GL_RGBA;
        
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, format, width1, height1, 0, format, GL_UNSIGNED_BYTE, data1);
        glGenerateMipmap(GL_TEXTURE_2D);
        stbi_image_free(data1);
    }

    // === BACKGROUND TEXTURE ===
    glGenTextures(1, &bgTexture);
    glBindTexture(GL_TEXTURE_2D, bgTexture);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    int bgWidth, bgHeight, bgChannels;
    unsigned char *bgData = stbi_load("Textures/background.jpg", &bgWidth, &bgHeight, &bgChannels, 0);

    if (!bgData) {
        std::cout << "Failed to load background texture: background.jpg" << std::endl;
    } else {
        GLenum bgFormat = GL_RGB;
        if (bgChannels == 1)
            bgFormat = GL_RED;
        else if (bgChannels == 3)
            bgFormat = GL_RGB;
        else if (bgChannels == 4)
            bgFormat = GL_RGBA;
        
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, bgFormat, bgWidth, bgHeight, 0, bgFormat, GL_UNSIGNED_BYTE, bgData);
        glGenerateMipmap(GL_TEXTURE_2D);
        stbi_image_free(bgData);
        std::cout << "Background texture loaded successfully!" << std::endl;
    }

    glm::vec3 cameraPos = glm::vec3(1.0f, 0.5f, 2.0f);
    glm::vec3 cameraTarget = glm::vec3(0.0f, -0.3f, -1.0f);
    glm::vec3 up = glm::vec3(0.0f, 1.0f, 0.0f); 

    glm::vec3 cameraDirection = glm::normalize(cameraTarget - cameraPos);
    glm::vec3 cameraRight = glm::normalize(glm::cross(up, cameraDirection));
    glm::vec3 cameraUp = glm::normalize(glm::cross(cameraRight, cameraDirection));

    glm::mat4 projection = glm::perspective(45.0f, (GLfloat)mainWindow.getBufferWidth() / (GLfloat)mainWindow.getBufferHeight(), 0.1f, 100.0f);

    // shadow
    GLuint depthMapFBO;
    glGenFramebuffers(1, &depthMapFBO);

    const GLuint SHADOW_WIDTH = 4096, SHADOW_HEIGHT = 4096;

    GLuint depthMap;
    glGenTextures(1, &depthMap);
    glBindTexture(GL_TEXTURE_2D, depthMap);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);

    float borderColor[] = {1.0, 1.0, 1.0, 1.0};
    glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);

    glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMap, 0);
    glDrawBuffer(GL_NONE);
    glReadBuffer(GL_NONE);
    glBindFramebuffer(GL_FRAMEBUFFER, 0);

    //Loop until window closed
    while (!mainWindow.getShouldClose())
    {   
        float currentTime = static_cast<float>(glfwGetTime());
        deltaTime = currentTime - lastTime;
        lastTime = currentTime;
        glfwPollEvents();
        checkMouse();

        glm::vec3 direction;
        direction.x = cos(glm::radians(yaw)) * cos(glm::radians(pitch));
        direction.y = sin(glm::radians(pitch));
        direction.z = sin(glm::radians(yaw)) * cos(glm::radians(pitch));
        cameraDirection = glm::normalize(direction);

        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_W) == GLFW_PRESS)
        {
            cameraPos += cameraDirection * 5.0f * deltaTime;
        }

        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_S) == GLFW_PRESS)
        {
            cameraPos -= cameraDirection * 5.0f * deltaTime;
        }

        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_A) == GLFW_PRESS)
        {
            cameraPos -= cameraRight * 5.0f * deltaTime;
        }

        if (glfwGetKey(mainWindow.getWindow(), GLFW_KEY_D) == GLFW_PRESS)
        {
            cameraPos += cameraRight * 5.0f * deltaTime;
        }

        cameraRight = glm::normalize(glm::cross(cameraDirection, up));
        cameraUp = glm::normalize(glm::cross(cameraRight, cameraDirection));

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shaderList[0]->UseShader();
        uniformModel = shaderList[0]->GetUniformLocation("model");
        uniformView = shaderList[0]->GetUniformLocation("view");
        uniformProjection = shaderList[0]->GetUniformLocation("projection");

        glm::mat4 view(1.0f);
        view = glm::lookAt(cameraPos, cameraPos + cameraDirection, cameraUp);

        lightPos.x = 1.0f + sin(glfwGetTime()) * 4.0f;
        lightPos.y = sin(glfwGetTime() / 2.0f) * 3.0f;
        lightPos.z = 6.0f;

        // first pass : render depth of scene to texture
        glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
        glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
        glClear(GL_DEPTH_BUFFER_BIT);

        glm::mat4 lightProjection, lightView;
        lightProjection = glm::ortho(-20.0f, 20.0f, -20.0f, 20.0f, 0.1f, 20.0f);
        lightView = glm::lookAt(lightPos, glm::vec3(0.0f, 0.0f, -2.5f), up);

        depthShader->UseShader();
        uniformModel = depthShader->GetUniformLocation("model");
        uniformView = depthShader->GetUniformLocation("view");
        uniformProjection = depthShader->GetUniformLocation("projection");

        shaderList[0]->UseShader();
        uniformModel = shaderList[0]->GetUniformLocation("model");
        uniformView = shaderList[0]->GetUniformLocation("view");
        uniformProjection = shaderList[0]->GetUniformLocation("projection");

        glCullFace(GL_FRONT);
        RenderScene(lightView, lightProjection);
        glCullFace(GL_BACK);

        // second pass : render scene as normal
        glViewport(0, 0, mainWindow.getBufferWidth(), mainWindow.getBufferHeight());
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        RenderScene(view, projection);

        // Light
        glUniform3fv(shaderList[0]->GetUniformLocation("lightColour"), 1, glm::value_ptr(lightColour));
        glUniform3fv(shaderList[0]->GetUniformLocation("lightPos"), 1, glm::value_ptr(lightPos));
        glUniform3fv(shaderList[0]->GetUniformLocation("viewPos"), 1, glm::value_ptr(cameraPos));

        // light mesh render
        shaderList[1]->UseShader();
        uniformModel = shaderList[1]->GetUniformLocation("model");
        uniformView = shaderList[1]->GetUniformLocation("view");
        uniformProjection = shaderList[1]->GetUniformLocation("projection");

        glm::mat4 model2(1.0f);

        model2 = glm::translate(model2, lightPos);
        model2 = glm::scale(model2, glm::vec3(0.2f, 0.2f, 0.2f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model2));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        glUniform3fv(shaderList[1]->GetUniformLocation("lightColour"), 1, glm::value_ptr(lightColour));
        light->RenderMesh();

        //bg
        shaderList[2]->UseShader();
        uniformModel = shaderList[2]->GetUniformLocation("model");
        uniformView = shaderList[2]->GetUniformLocation("view");
        uniformProjection = shaderList[2]->GetUniformLocation("projection");

        glm::mat4 model(1.0f);
        model = glm::translate(model, glm::vec3(0.0f, 0.0f, -8.0f));
        model = glm::scale(model, glm::vec3(20.0f, 20.0f, 0.1f));

        glUniformMatrix4fv(uniformModel, 1, GL_FALSE, glm::value_ptr(model));
        glUniformMatrix4fv(uniformView, 1, GL_FALSE, glm::value_ptr(view));
        glUniformMatrix4fv(uniformProjection, 1, GL_FALSE, glm::value_ptr(projection));

        // bg color
        glm::vec3 bgcolour = glm::vec3(1.0f, 1.0f, 1.0f); 
        glUniform3fv(shaderList[2]->GetUniformLocation("bgColour"), 1, (GLfloat *)&bgcolour);
        
        glUniformMatrix4fv(shaderList[2]->GetUniformLocation("lightProjection"), 1, GL_FALSE, glm::value_ptr(lightProjection));
        glUniformMatrix4fv(shaderList[2]->GetUniformLocation("lightView"), 1, GL_FALSE, glm::value_ptr(lightView));

        // Bind shadow map
        GLuint uniformShadowMap = shaderList[2]->GetUniformLocation("shadowMap");
        glUniform1i(uniformShadowMap, 1);
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, depthMap);

        // Bind background texture
        GLuint uniformBgTexture = shaderList[2]->GetUniformLocation("bgTexture");
        glUniform1i(uniformBgTexture, 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, bgTexture);

        bg->RenderMesh();

        glUseProgram(0);

        mainWindow.swapBuffers();
    }

    return 0;
}