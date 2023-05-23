ENCODED_GOOGLE_SERVICES_CONFIG=$1

echo "$ENCODED_GOOGLE_SERVICES_CONFIG" > google-services-b64.txt
base64 -d google-services-b64.txt > "app/google-services.json"