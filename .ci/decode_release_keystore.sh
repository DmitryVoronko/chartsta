ENCODED_KEYSTORE=$1
ENCODED_KEYSTORE_PROPERTIES=$2

echo "$ENCODED_KEYSTORE" > keystore-b64.txt
base64 -d keystore-b64.txt > "app/ks/release.keystore"
echo "$ENCODED_KEYSTORE_PROPERTIES" > keystore-properties-b64.txt
base64 -d keystore-properties-b64.txt > "app/ks/keystore.properties"