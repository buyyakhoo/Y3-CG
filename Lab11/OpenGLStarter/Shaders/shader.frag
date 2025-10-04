#version 330 core

out vec4 colour;
in vec4 vCol;
in vec2 TexCoord;

uniform vec3 lightColour;

uniform sampler2D texture1;

void main()
{
    // vec4 tex1 = texture(texture1, TexCoord);
    // vec4 tex2 = texture(texture2, TexCoord);

    // colour = texture(texture1, TexCoord) * vCol; 

    float ambientStrength = 0.3f;
    vec3 ambient = ambientStrength * lightColour;
    colour = texture(texture1, TexCoord) * vec4(ambient, 1.0);

    // colour = mix(tex1, tex2, tex2.a);
}
