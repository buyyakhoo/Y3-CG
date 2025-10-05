#version 330 core

out vec4 colour;
in vec4 vCol;
in vec2 TexCoord;
in vec3 Normal;
in vec3 FragPos;

uniform vec3 lightColour;
uniform vec3 lightPos;
uniform vec3 viewPos;

uniform sampler2D texture1;

vec3 ambientLight() {
    float ambientStrength = 0.1f;
    vec3 ambient = ambientStrength * lightColour;
    return ambient;
}

vec3 diffuseLight() {
    float diffuseStrength = 0.8f;
    
    vec3 lightDir = normalize(lightPos - FragPos);
    vec3 norm = normalize(Normal);

    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = lightColour * diff * diffuseStrength;
    return diffuse;
}

vec3 specularLight() {
    float specularStrength = 0.5f;
    float shininess = 64.0f;

    vec3 lightDir = normalize(lightPos - FragPos);
    vec3 norm = normalize(Normal);
    vec3 reflectDir = reflect(-lightDir, norm);
    vec3 viewDir = normalize(viewPos - FragPos);

    float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);

    vec3 specular = lightColour * spec * specularStrength;

    return specular;
}

void main()
{
    // vec4 tex1 = texture(texture1, TexCoord);
    // vec4 tex2 = texture(texture2, TexCoord);

    // colour = texture(texture1, TexCoord) * vCol; 

    colour = texture(texture1, TexCoord) * vec4(ambientLight() + diffuseLight() + specularLight(), 1.0);

    // colour = mix(tex1, tex2, tex2.a);
}
