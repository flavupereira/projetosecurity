# Script para testar a API de Produtos
Write-Host "=== TESTE DA API ===" -ForegroundColor Cyan

# 1. Registrar usuario
Write-Host "`n1. Registrando usuario..." -ForegroundColor Yellow
$regJson = '{"login":"admin","password":"123456","role":"ADMIN"}'
try {
    $reg = Invoke-RestMethod -Uri "http://localhost:8080/auth/register" -Method Post -ContentType "application/json" -Body $regJson
    Write-Host "   OK: Usuario registrado" -ForegroundColor Green
} catch {
    Write-Host "   AVISO: Usuario pode ja existir" -ForegroundColor Yellow
}

# 2. Login
Write-Host "`n2. Fazendo login..." -ForegroundColor Yellow
$loginJson = '{"login":"admin","password":"123456"}'
$login = Invoke-RestMethod -Uri "http://localhost:8080/auth/login" -Method Post -ContentType "application/json" -Body $loginJson
$token = $login.token
Write-Host "   OK: Login realizado" -ForegroundColor Green
Write-Host "   Token: $($token.Substring(0, 30))..." -ForegroundColor Gray

# 3. Adicionar produto
Write-Host "`n3. Adicionando produto..." -ForegroundColor Yellow
$prodJson = '{"nome":"Notebook Dell","price":3500}'
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

try {
    $produto = Invoke-RestMethod -Uri "http://localhost:8080/product" -Method Post -Headers $headers -Body $prodJson
    Write-Host "   OK: Produto adicionado" -ForegroundColor Green
    Write-Host "   ID: $($produto.id)" -ForegroundColor Cyan
    Write-Host "   Nome: $($produto.nome)" -ForegroundColor Cyan
    Write-Host "   Preco: $($produto.price)" -ForegroundColor Cyan
} catch {
    Write-Host "   ERRO: $($_.Exception.Message)" -ForegroundColor Red
}

# 4. Listar produtos
Write-Host "`n4. Listando produtos..." -ForegroundColor Yellow
try {
    $produtos = Invoke-RestMethod -Uri "http://localhost:8080/product" -Method Get -Headers @{"Authorization" = "Bearer $token"}
    Write-Host "   Total de produtos: $($produtos.Count)" -ForegroundColor Green
    
    foreach ($p in $produtos) {
        Write-Host "   - $($p.nome) (R$ $($p.price))" -ForegroundColor Cyan
    }
} catch {
    Write-Host "   ERRO ao listar: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== FIM DO TESTE ===" -ForegroundColor Cyan
Pause