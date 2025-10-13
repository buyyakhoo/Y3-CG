#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 aTexCoord;  // เพิ่ม texture coordinate input

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform mat4 lightProjection;
uniform mat4 lightView;

out vec4 fragPosLightSpace; 
out vec2 TexCoord;  // ส่ง texture coordinate ไปที่ fragment shader

void main()
{
    vec3 fragPos = vec3(model * vec4(pos, 1.0));
    fragPosLightSpace = lightProjection * lightView * vec4(fragPos, 1.0);
    gl_Position = projection * view * model * vec4(pos, 1.0);
    
    TexCoord = aTexCoord;  // ส่ง texture coordinate
}