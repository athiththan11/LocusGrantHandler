# Locus Grant Handler

A custom `Grant Handler` & `Grant Validator` implementation for `WSO2 API Manager`

## Implementation & Usage

The developed custom grant expects the following to authenticate and generate access tokens

* name: a unique name
* location: supported location
* key: a unique key value

Given below are a set of sample values

* name: `locus-10-09874`, `ocus-10-01219`
* location: `LK-COLOMBO`, `LK-KANDY`
* key: `0987654321`, `1234567890`

### Configuring & Deploying

Use the following command to build the project

```shell
mvn clean package
```

Copy and place the built JAR artifact inside the `<APIM>/repository/components/lib` directory. Then navigate and open the `<APIM>/repository/conf/identity/identity.xml` and add the our custom grant under the `<SupportedGrantTypes>` section as follows

```xml
<SupportedGrantTypes>
    <SupportedGrantType>
        <GrantTypeName>locus</GrantTypeName>
        <GrantTypeHandlerImplClass>com.grant.sample.handler.LocusGrantHandler</GrantTypeHandlerImplClass>
        <GrantTypeValidatorImplClass>com.grant.sample.validator.LocusGrantValidator</GrantTypeValidatorImplClass>
        <IdTokenAllowed>false</IdTokenAllowed>
    </SupportedGrantType>
</SupportedGrantTypes>
```

### Test Case

Fire up the `WSO2 API Manager` server by navigating to `<APIM>/bin` directory and executing the following based on your OS

```shell
# linux env
sh wso2server.sh

# windows env
wso2server.bat
```

Route to [Carbon Management Console '/carbon'](`https://localhost:9443/carbon`), and direct to `Main > Identity > Service Providers > Add` and create a new `Service Provider` for demo purposes.

Register an `OAuth/OpenID Connect` and select the `locus` Grant Type. Copy the `Client ID` and the `Client Secret` of the Service Provider to invoke the Token endpoint.

Given below is a sample `cURL` command to invoke the `Token` endpoint of `WSO2 API Manager` with our developed custom grant

```shell
curl --location --request POST 'https://localhost:8243/token' \
    --header 'Authorization: Basic {base64<ClientID:Client Secret>}' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=locus' \
    --data-urlencode 'name=locus-10-09874' \
    --data-urlencode 'location=LK-COLOMBO' \
    --data-urlencode 'key=0987654321'
```
