#version 330 core

out vec4 colour;

in vec4 fragPosLightSpace;

uniform vec3 bgColour;
uniform sampler2D shadowMap;

float ShadowCalculation()
{
    float shadow = 0.0;

    // perform perspective divide
    vec3 projCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
    // transform to [0,1] range
    projCoords = projCoords * 0.5 + 0.5;
    // get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
    float closestDepth = texture(shadowMap, projCoords.xy).r;
    // get depth of current fragment from light's perspective
    float currentDepth = projCoords.z;

    // // check whether current frag pos is in shadow
    shadow = currentDepth > closestDepth  ? 1.0 : 0.0;
    
    return shadow;
}

void main() 
{
    float shadow = ShadowCalculation();
    colour = vec4((1.0 - shadow) * bgColour, 1.0);
}