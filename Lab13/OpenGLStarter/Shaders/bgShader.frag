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
    float bias = 0.005;
    shadow = currentDepth - bias > closestDepth  ? 0.7 : 0.0;

    vec2 texelSize = 1.0 / textureSize(shadowMap, 0);
    for(int x = -1; x <= 1; ++x)
    {
        for(int y = -1; y <= 1; ++y)
        {
            float pcfDepth = texture(shadowMap, projCoords.xy + vec2(x, y) * texelSize).r;
            shadow += currentDepth - bias > pcfDepth  ? 1.0 : 0.0;
        }
    }
    shadow /= 9.0;

    
    return shadow;
}

void main() 
{
    float shadow = ShadowCalculation();
    if (shadow > 0) colour = vec4(0.0, 0.0f, 0.0f, 1.0f);
    else colour = vec4(1.0, 0.5f, 0.5f, 1.0f);

    // colour = vec4((1.0 - shadow) * bgColour, 1.0);
}