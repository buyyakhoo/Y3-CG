#version 330 core

layout (location = 0) in vec3 pos;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform mat4 lightProjection;
uniform mat4 lightView;

out vec4 fragPosLightSpace; 

void main()
{
    vec4 fragPos = vec3(model * vec4(pos, 1.0));
    fragPosLightSpace = lightProjection * lightView * vec4(fragPos, 1.0);
    gl_Position = projection * view * model * vec4(pos.x, pos.y, pos.z, 1.0);
}