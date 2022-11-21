//Capacidad -> 1 Consulta de todos los libros
pm.test("Validacion de propiedades requeridas de retorno", function () {

    pm.expect(pm.response.text()).to.include("id");
    pm.expect(pm.response.text()).to.include("nombre");
    pm.expect(pm.response.text()).to.include("autor");
    pm.expect(pm.response.text()).to.include("editorial");
    pm.expect(pm.response.text()).to.include("fecha");
    pm.expect(pm.response.text()).to.include("numeroPag");
    pm.expect(pm.response.text()).to.include("stock");
    pm.expect(pm.response.text()).to.include("valor");
    pm.expect(pm.response.text()).to.include("isbn");
   
});

pm.test("Validar todos los status", function () {
pm.expect(pm.response.code).to.be.oneOf([200,204,304,400,404,405,500]);
});

pm.test("Tiempo de respuesta es menor a 1000ms", () => {
  pm.expect(pm.response.responseTime).to.be.below(1000);
});

pm.test("Si la respuesta contienen Content-Type ", () => {
  pm.response.to.have.header("Content-Type");
});

pm.test("Validar formato del Content-Type de la respuesta", () => {
  pm.expect(pm.response.headers.get('Content-Type')).to.eql('application/json');
});

pm.test("Validar formato del Content-Type", () => {
  pm.expect(pm.response.headers.get('Content-Type')).to.eql('application/json');
});


